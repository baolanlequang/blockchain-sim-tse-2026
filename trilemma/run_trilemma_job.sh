#!/bin/bash
#SBATCH --job-name=run_trilemma_job
#SBATCH --output=logs/trilemma_%A_%a.out
#SBATCH --error=logs/trilemma_%A_%a.err
#SBATCH --partition=highmem
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=96
#SBATCH --time=01:00:00
#SBATCH --array=0-99
#SBATCH --mail-type=BEGIN,END,FAIL
#SBATCH --mail-user=baolan2005@gmail.com

# ---------------------------------------------------------------------------------------------
# SLURM ARRAY JOB — refined-engine hierarchical manifest runner.
#
# The refined engine (requireHierarchicalManifest=true in the config JSON) executes exactly
# ONE simulation per manifest row, sequentially within a single JVM -- there is no internal
# Monte Carlo loop and no intra-JVM parallelism across rounds (unlike the old
# engine, which ran up to 96 Monte Carlo rounds concurrently per pair). Each array task
# slices ROWS_PER_TASK manifest rows out of the full manifest CSV and passes that slice to
# a single `java -jar` call, writing run_id-named result files so tasks never collide.
# trilemma.jar loops over every row of whatever CSV it's handed itself (it has no
# --row-index flag like atosim.jar), so batching here happens by slicing the CSV per task
# rather than by looping java calls per row within a task (contrast run_selfish.sh in ATOSIM).
#
# ROW_OFFSET/ROWS_PER_TASK follow the same convention as ATOSIM's submit_chunked_array.sh, so
# this script can be driven by it directly (0-indexed SLURM_ARRAY_TASK_ID, offset applied before
# converting to a 1-indexed position within the manifest's DATA rows, i.e. excluding the header):
#   start = ROW_OFFSET + SLURM_ARRAY_TASK_ID * ROWS_PER_TASK + 1
#   end   = start + ROWS_PER_TASK - 1
#
# ROWS_PER_TASK and the #SBATCH --array default above MUST stay consistent for a direct `sbatch
# run_trilemma_job.sh` submission: array size = ceil(N_MANIFEST_ROWS / ROWS_PER_TASK).
# The manifest is now a hierarchical R_S x R_E replication manifest (one row per explicit
# execution, e.g. preliminary_64x48_run/manifest_64x48_rs2re2_master1024_full.csv, 12,288 rows
# for the full 64x48 set), NOT the old pair_id-per-row nested_trilemma.csv. Recompute
# ROWS_PER_TASK/--array for whatever manifest is actually being submitted -- see the small
# test-batch invocation this script was prepared alongside for a concrete example.
# To use a different ROWS_PER_TASK, resume from an offset, or stay under a cluster MaxArraySize,
# submit via:
#   ./submit_chunked_array.sh run_trilemma_job.sh <manifest_row_count> [chunk_size] [sleep_between] [start_offset] [rows_per_task]
#
# Per-task walltime: kept at a generous but FINITE 1 hour (rather than the old 72h budget
# carried over from the internal-Monte-Carlo-loop engine). A local-machine benchmark found at
# least one in-range 64x48 row (D121_O063, N_V=159, high transaction-arrival-rate=93/s)
# causes BehaviorUtils.kt's reconcileMempoolAfterAppend() to either OOM (424s at a 4GB heap)
# or run 36+ minutes without completing (14GB heap, CPU-bound, not GC-bound) on a 16-core/16GB
# local machine. That bug is NOT fixed by this script -- a 1h cap bounds the damage from a
# stuck/pathological row to at most ~1h of wasted allocation per task instead of silently
# consuming the full walltime budget (or hanging indefinitely), and is generous enough to let
# a handful of ordinary rows plus one slow row complete on this cluster's (likely faster
# per-core) hardware. Recheck this cap once real per-row timings from this cluster are known.
# ---------------------------------------------------------------------------------------------
# DEPENDENCY: the config JSON's declaredSamplePairs/allowValidatedPartialBatch pairing assumes
# ROWS_PER_TASK=4 (one design/operational pair's full R_S=2 x R_E=2 replication set per task).
# TrilemmaSimulator.java's validateDeclaredSamplePairCount() checks distinct manifest_pair_id
# values against declaredSamplePairs, but it only ever sees SLICE_CSV (this task's slice, per
# the -jar invocation below), never FULL_CSV -- so declaredSamplePairs is deliberately left at
# the whole-batch pair count and allowValidatedPartialBatch=true skips that per-task check
# instead of setting declaredSamplePairs=1, which would silently misrepresent the experiment
# scale in every result JSON and would need re-deriving again if ROWS_PER_TASK ever changes to
# batch more than one pair per task. If ROWS_PER_TASK is changed from 4, this dependency still
# holds regardless (the check stays skipped either way) -- but re-confirm distinct-pair counts
# per task manually before trusting a larger/irregular ROWS_PER_TASK.
ROWS_PER_TASK=${ROWS_PER_TASK:-5}

# Overridable (this cluster has no java module -- only direct JVM installs under
# /usr/lib/jvm/ -- and the bare `java` on PATH resolves to a pre-21 JVM that cannot load
# trilemma.jar's class file version 65.0). /usr/lib/jvm/java-21/ is the stable symlink
# alongside its versioned target (e.g. java-21-openjdk-21.0.12.1.1-1.1.el9.x86_64/);
# using the symlink so this keeps working across a minor JDK point-release bump without
# editing this script again.
JAVA_BIN=${JAVA_BIN:-/usr/lib/jvm/java-21/bin/java}
if [ ! -x "$JAVA_BIN" ]; then
    echo "ERROR: JAVA_BIN '$JAVA_BIN' not found or not executable. Set JAVA_BIN to a Java 21+ binary." >&2
    exit 1
fi
echo "Using JAVA_BIN=$JAVA_BIN ($("$JAVA_BIN" -version 2>&1 | head -1))"

SUBMIT_DIR="$(pwd)"
# Overridable (same pattern as ROWS_PER_TASK/RESULTS_WORKSPACE below) so this one script can
# submit either the small test batch or the full 64x48 run without a second copy, e.g.:
#   sbatch --export=ALL,FULL_CSV=...,BASE_CONFIG=...,ROWS_PER_TASK=4 --array=0-5 run_trilemma_job.sh
FULL_CSV=${FULL_CSV:-org.palladiosimulator.blockchainsystems.trilemma/preliminary_64x48_run/manifest_64x48_rs2re2_master1024_full.csv}
TESTMODELS=org.palladiosimulator.blockchainsystems.trilemma/testmodels
BASE_CONFIG=${BASE_CONFIG:-org.palladiosimulator.blockchainsystems.trilemma/preliminary_64x48_run/configuration_refined_64x48_full.json}

mkdir -p logs

# Final results and this task's Slurm log files are copied into a bwUniCluster 3.0
# workspace at the end of the task, since $HOME/Lustre has a small quota meant for
# source/config files, not bulk simulation output - allocate one before submitting
# this job:
#   ws_allocate trilemma_results 30
RESULTS_WORKSPACE="${RESULTS_WORKSPACE:-trilemma_results}"
WORKSPACE_PATH="$(ws_find "${RESULTS_WORKSPACE}")"
if [ -z "${WORKSPACE_PATH}" ]; then
    echo "ERROR: workspace '${RESULTS_WORKSPACE}' not found. Allocate it first: ws_allocate ${RESULTS_WORKSPACE} <days>" >&2
    exit 1
fi
RESULTS_DIR="${WORKSPACE_PATH}/trilemma"
LOGS_DIR="${WORKSPACE_PATH}/logs"
mkdir -p "${RESULTS_DIR}" "${LOGS_DIR}"

# Data-row range (1-indexed, excluding the header) handled by this task.
start=$(( ${ROW_OFFSET:-0} + SLURM_ARRAY_TASK_ID * ROWS_PER_TASK + 1 ))
end=$(( start + ROWS_PER_TASK - 1 ))

# Build a per-task CSV slice by LINE POSITION: header (file line 1) + data rows whose
# position within the data rows (file line number minus the header line) falls in [start,end].
# The manifest's one row = one execution; there is no numeric per-row id column to compare
# against (design_id/manifest_pair_id are strings like "D121_O063"), so slicing must be by
# position, not by any column value.
SLICE_CSV="${SUBMIT_DIR}/slice_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.csv"
awk -v s="$start" -v e="$end" 'NR==1 || (NR-1>=s && NR-1<=e)' "$FULL_CSV" > "$SLICE_CSV"

echo "Task ${SLURM_ARRAY_TASK_ID}: manifest rows ${start}-${end} ($(($(wc -l < "$SLICE_CSV") - 1)) rows)"

# Run from a private per-task directory on local SSD ($TMPDIR) rather than directly
# in $SUBMIT_DIR on Lustre, matching ATOSIM's run_selfish.sh pattern of writing
# results to local SSD during the job and bulk-copying to the workspace once at the
# end (avoids hammering the network filesystem with many small per-pair JSON
# writes). Unlike atosim.jar, trilemma.jar has no --output-dir flag: it always
# writes to ./result_trilemma/ relative to CWD, and BlockchainSystemModelLoader
# resolves testmodels/ the same CWD-relative way. So instead of redirecting the
# output path, this task cd's into TASK_WORKDIR with trilemma.jar and the
# org.palladiosimulator.blockchainsystems.trilemma/ project symlinked in, which
# keeps the exact relative layout both expect while every write lands on local SSD.
TASK_WORKDIR="${TMPDIR}/trilemma_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}"
mkdir -p "${TASK_WORKDIR}/result_trilemma"
ln -s "${SUBMIT_DIR}/org.palladiosimulator.blockchainsystems.trilemma" "${TASK_WORKDIR}/org.palladiosimulator.blockchainsystems.trilemma"
ln -s "${SUBMIT_DIR}/trilemma.jar" "${TASK_WORKDIR}/trilemma.jar"
cd "${TASK_WORKDIR}"

# Single-threaded JVM: the refined engine processes exactly one execution at a time
# (engineSimulationType=Single, engineNumberOfMonteCarloRounds=1), sequentially across
# whatever rows this task's slice contains -- there is no 96-way internal parallelism to
# size heap against here, unlike the old engine's 96-concurrent-round model this file
# previously assumed (hence the old -Xmx900G/-Xms256G/-XX:ParallelGCThreads=96).
# -Xmx64G is a generous allocation for a single sequential execution (a local-machine
# benchmark saw a single problematic execution reach ~4.4GB RSS before OOMing at a 4GB
# cap, and not exhaust a 14GB cap after 36+ minutes) -- 64G leaves wide headroom given the
# known-unresolved memory-growth bug noted above, while remaining a small fraction of this
# partition's ~1.13TB entitlement for a 96-cpu allocation (96 x default 12090 MB/core),
# so a stuck/pathological row is expected to hit the 1h walltime cap rather than OOM first.
"$JAVA_BIN" -Xms8G -Xmx64G \
     -XX:+UseG1GC \
     -XX:ParallelGCThreads=8 \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath="${TASK_WORKDIR}/heapdump_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.hprof" \
     -jar trilemma.jar \
     "$SLICE_CSV" \
     "$TESTMODELS" \
     "$BASE_CONFIG"

cd "${SUBMIT_DIR}"
rm -f "$SLICE_CSV"

# Bulk-copy this task's results and Slurm log files from local SSD into the workspace.
cp "${TASK_WORKDIR}/result_trilemma"/result_*.json "${RESULTS_DIR}/" 2>/dev/null || true
cp "logs/trilemma_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.out" "${LOGS_DIR}/" 2>/dev/null || true
cp "logs/trilemma_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.err" "${LOGS_DIR}/" 2>/dev/null || true
