#!/bin/bash
#SBATCH --job-name=run_trilemma_job
#SBATCH --output=logs/trilemma_%A_%a.out
#SBATCH --error=logs/trilemma_%A_%a.err
#SBATCH --partition=highmem
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=8
#SBATCH --mem=80G
#SBATCH --time=01:00:00
#SBATCH --array=0-99
#SBATCH --mail-type=BEGIN,END,FAIL
#SBATCH --mail-user=baolan2005@gmail.com

set -uo pipefail

ROWS_PER_TASK=${ROWS_PER_TASK:-4}

JAVA_BIN=${JAVA_BIN:-/usr/lib/jvm/java-21/bin/java}
if [ ! -x "$JAVA_BIN" ]; then
    echo "ERROR: JAVA_BIN '$JAVA_BIN' not found or not executable." >&2
    exit 1
fi

SUBMIT_DIR="$(pwd)"
FULL_CSV=${FULL_CSV:-org.palladiosimulator.blockchainsystems.trilemma/preliminary_64x48_run/manifest_64x48_rs2re2_master1024_full.csv}
TESTMODELS=org.palladiosimulator.blockchainsystems.trilemma/testmodels
BASE_CONFIG=${BASE_CONFIG:-org.palladiosimulator.blockchainsystems.trilemma/preliminary_64x48_run/configuration_refined_64x48_full.json}

case "$FULL_CSV" in
    /*) FULL_CSV_PATH="$FULL_CSV" ;;
    *)  FULL_CSV_PATH="${SUBMIT_DIR}/${FULL_CSV}" ;;
esac

if [ ! -f "$FULL_CSV_PATH" ]; then
    echo "ERROR: manifest not found: $FULL_CSV_PATH" >&2
    exit 1
fi
if [ ! -f "${SUBMIT_DIR}/trilemma.jar" ]; then
    echo "ERROR: trilemma.jar not found in $SUBMIT_DIR" >&2
    exit 1
fi
if [ ! -d "${SUBMIT_DIR}/org.palladiosimulator.blockchainsystems.trilemma" ]; then
    echo "ERROR: trilemma project directory not found in $SUBMIT_DIR" >&2
    exit 1
fi

# Previously validated V4 containment envelope. Each value can be overridden at submission.
CANONICAL_PROGRESS_STALL_MS=${CANONICAL_PROGRESS_STALL_MS:-604800000}
MAX_TRANSACTION_SUBMISSIONS=${MAX_TRANSACTION_SUBMISSIONS:-5000000}
MAX_BLOCK_PROPOSALS=${MAX_BLOCK_PROPOSALS:-100000}
MAX_PROCESSED_EVENTS=${MAX_PROCESSED_EVENTS:-40000000}
MAX_FUTURE_EVENTS=${MAX_FUTURE_EVENTS:-100000}
MAX_TRANSACTION_KNOWLEDGE_ENTRIES=${MAX_TRANSACTION_KNOWLEDGE_ENTRIES:-5000000}
MAX_BLOCK_KNOWLEDGE_ENTRIES=${MAX_BLOCK_KNOWLEDGE_ENTRIES:-5000000}
MAX_MEMPOOL_ENTRIES=${MAX_MEMPOOL_ENTRIES:-5000000}
MAX_MEASUREMENT_TRANSACTION_ENTRIES=${MAX_MEASUREMENT_TRANSACTION_ENTRIES:-2000000}

mkdir -p "${SUBMIT_DIR}/logs"

RESULTS_WORKSPACE="${RESULTS_WORKSPACE:-trilemma_results}"
WORKSPACE_PATH="$(ws_find "${RESULTS_WORKSPACE}")"
if [ -z "${WORKSPACE_PATH}" ]; then
    echo "ERROR: workspace '${RESULTS_WORKSPACE}' not found." >&2
    exit 1
fi

RESULTS_DIR="${WORKSPACE_PATH}/trilemma"
LOGS_DIR="${WORKSPACE_PATH}/logs"
mkdir -p "${RESULTS_DIR}" "${LOGS_DIR}"

start=$(( ${ROW_OFFSET:-0} + SLURM_ARRAY_TASK_ID * ROWS_PER_TASK + 1 ))
end=$(( start + ROWS_PER_TASK - 1 ))

TASK_WORKDIR="${TMPDIR}/trilemma_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}"
mkdir -p "${TASK_WORKDIR}"

STATUS_FILE="${TASK_WORKDIR}/status_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.csv"
RUNTIME_FILE="${TASK_WORKDIR}/runtime_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.txt"

echo "manifest_row,run_id,status,java_exit,termination_reason,result_file" > "$STATUS_FILE"

SOURCE_COMMIT="$(git -C "$SUBMIT_DIR" rev-parse HEAD 2>/dev/null || echo UNKNOWN)"
JAR_SHA256="$(sha256sum "${SUBMIT_DIR}/trilemma.jar" | awk '{print $1}')"

cat > "$RUNTIME_FILE" <<EOF
source_commit=${SOURCE_COMMIT}
jar_sha256=${JAR_SHA256}
java=$("$JAVA_BIN" -version 2>&1 | head -1)
xms=8G
xmx=64G
canonicalProgressStallMs=${CANONICAL_PROGRESS_STALL_MS}
maxTransactionSubmissions=${MAX_TRANSACTION_SUBMISSIONS}
maxBlockProposals=${MAX_BLOCK_PROPOSALS}
maxProcessedEvents=${MAX_PROCESSED_EVENTS}
maxFutureEvents=${MAX_FUTURE_EVENTS}
maxTransactionKnowledgeEntries=${MAX_TRANSACTION_KNOWLEDGE_ENTRIES}
maxBlockKnowledgeEntries=${MAX_BLOCK_KNOWLEDGE_ENTRIES}
maxMempoolEntries=${MAX_MEMPOOL_ENTRIES}
maxMeasurementTransactionEntries=${MAX_MEASUREMENT_TRANSACTION_ENTRIES}
EOF

echo "Task ${SLURM_ARRAY_TASK_ID}: manifest rows ${start}-${end}"
cat "$RUNTIME_FILE"

copy_task_metadata() {
    cp "$STATUS_FILE" "$LOGS_DIR/" 2>/dev/null || true
    cp "$RUNTIME_FILE" "$LOGS_DIR/" 2>/dev/null || true
    cp "${SUBMIT_DIR}/logs/trilemma_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.out" "$LOGS_DIR/" 2>/dev/null || true
    cp "${SUBMIT_DIR}/logs/trilemma_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.err" "$LOGS_DIR/" 2>/dev/null || true
}
trap copy_task_metadata EXIT TERM INT

task_failed=0
rows_seen=0

for (( global_row=start; global_row<=end; global_row++ )); do
    RUN_WORKDIR="${TASK_WORKDIR}/row_${global_row}"
    mkdir -p "${RUN_WORKDIR}/result_trilemma"

    ln -s "${SUBMIT_DIR}/org.palladiosimulator.blockchainsystems.trilemma"           "${RUN_WORKDIR}/org.palladiosimulator.blockchainsystems.trilemma"
    ln -s "${SUBMIT_DIR}/trilemma.jar" "${RUN_WORKDIR}/trilemma.jar"

    ONE_ROW_CSV="${RUN_WORKDIR}/manifest_row_${global_row}.csv"
    awk -v n="$global_row" 'NR==1 || NR==n+1' "$FULL_CSV_PATH" > "$ONE_ROW_CSV"

    if [ "$(wc -l < "$ONE_ROW_CSV")" -lt 2 ]; then
        rm -rf "$RUN_WORKDIR"
        break
    fi

    rows_seen=$((rows_seen + 1))
    STDOUT_LOG="${RUN_WORKDIR}/stdout.log"
    STDERR_LOG="${RUN_WORKDIR}/stderr.log"
    HEAP_DUMP="${RUN_WORKDIR}/heapdump.hprof"

    echo "=== manifest row ${global_row}: starting fresh JVM ==="

    (
        cd "$RUN_WORKDIR" || exit 98
        "$JAVA_BIN" -Xms8G -Xmx64G             -XX:+UseG1GC             -XX:ParallelGCThreads=8             -XX:+ExitOnOutOfMemoryError             -XX:+HeapDumpOnOutOfMemoryError             -XX:HeapDumpPath="$HEAP_DUMP"             -Dthreesim.canonicalProgressStallMs="$CANONICAL_PROGRESS_STALL_MS"             -Dthreesim.maxTransactionSubmissions="$MAX_TRANSACTION_SUBMISSIONS"             -Dthreesim.maxBlockProposals="$MAX_BLOCK_PROPOSALS"             -Dthreesim.maxProcessedEvents="$MAX_PROCESSED_EVENTS"             -Dthreesim.maxFutureEvents="$MAX_FUTURE_EVENTS"             -Dthreesim.maxTransactionKnowledgeEntries="$MAX_TRANSACTION_KNOWLEDGE_ENTRIES"             -Dthreesim.maxBlockKnowledgeEntries="$MAX_BLOCK_KNOWLEDGE_ENTRIES"             -Dthreesim.maxMempoolEntries="$MAX_MEMPOOL_ENTRIES"             -Dthreesim.maxMeasurementTransactionEntries="$MAX_MEASUREMENT_TRANSACTION_ENTRIES"             -jar trilemma.jar             "$ONE_ROW_CSV"             "$TESTMODELS"             "$BASE_CONFIG"
    ) >"$STDOUT_LOG" 2>"$STDERR_LOG"

    java_exit=$?

    shopt -s nullglob
    results=("${RUN_WORKDIR}"/result_trilemma/result_*.json)
    shopt -u nullglob
    result_count=${#results[@]}

    oom_detected=0
    if grep -Eqi "OutOfMemoryError|Java heap space|GC overhead limit exceeded"         "$STDOUT_LOG" "$STDERR_LOG" 2>/dev/null; then
        oom_detected=1
    fi

    audit_ok=0
    result_file=""
    run_id="ROW_${global_row}"
    termination_reason="UNKNOWN"

    if [ "$result_count" -eq 1 ]; then
        result_file="${results[0]}"
        base="$(basename "$result_file")"
        run_id="${base#result_config_}"
        run_id="${run_id%.json}"

        if grep -q '"refinedExecutionAudit"' "$result_file"; then
            audit_ok=1
        fi

        extracted_reason="$(
            grep -oE '"terminationReason"[[:space:]]*:[[:space:]]*"[^"]*"' "$result_file" 2>/dev/null             | head -1             | sed -E 's/.*"terminationReason"[[:space:]]*:[[:space:]]*"([^"]*)".*/\1/'
        )"
        if [ -n "$extracted_reason" ]; then
            termination_reason="$extracted_reason"
        fi
    fi

    if [ "$oom_detected" -eq 1 ]; then
        status="OOM_FAILURE"
        task_failed=1
    elif [ "$java_exit" -eq 0 ] && [ "$result_count" -eq 1 ] && [ "$audit_ok" -eq 1 ]; then
        status="COMPLETE"
    elif [ "$java_exit" -eq 3 ] && [ "$result_count" -eq 1 ] && [ "$audit_ok" -eq 1 ]; then
        status="CONTROLLED_INCOMPLETE"
    else
        status="FAILURE"
        task_failed=1
    fi

    if [ "$result_count" -gt 0 ]; then
        cp "${RUN_WORKDIR}"/result_trilemma/result_*.json "$RESULTS_DIR/" 2>/dev/null || true
    fi

    safe_id="$(printf '%s' "$run_id" | tr -c 'A-Za-z0-9._-' '_')"
    cp "$STDOUT_LOG" "${LOGS_DIR}/stdout_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}_${safe_id}.log" 2>/dev/null || true
    cp "$STDERR_LOG" "${LOGS_DIR}/stderr_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}_${safe_id}.log" 2>/dev/null || true

    result_name=""
    if [ -n "$result_file" ]; then
        result_name="$(basename "$result_file")"
    fi

    echo "${global_row},${run_id},${status},${java_exit},${termination_reason},${result_name}" >> "$STATUS_FILE"
    echo "=== ${run_id}: ${status}; exit=${java_exit}; reason=${termination_reason} ==="

    if [ "${COPY_HEAP_DUMPS:-0}" = "1" ] && [ -f "$HEAP_DUMP" ]; then
        cp "$HEAP_DUMP" "${LOGS_DIR}/heapdump_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}_${safe_id}.hprof" 2>/dev/null || true
    fi
done

copy_task_metadata

if [ "$rows_seen" -eq 0 ]; then
    echo "No manifest rows assigned to this task."
    exit 0
fi

if [ "$task_failed" -ne 0 ]; then
    echo "Task completed with one or more OOM/unexpected failures." >&2
    exit 1
fi

echo "Task completed: all assigned rows were COMPLETE or CONTROLLED_INCOMPLETE."
exit 0
