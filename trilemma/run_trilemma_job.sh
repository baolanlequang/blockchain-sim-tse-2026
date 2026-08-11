#!/bin/bash
#SBATCH --job-name=run_trilemma_job
#SBATCH --output=logs/trilemma_%A_%a.out
#SBATCH --error=logs/trilemma_%A_%a.err
#SBATCH --partition=highmem
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=96
#SBATCH --time=72:00:00
#SBATCH --array=0-99
#SBATCH --mail-type=BEGIN,END,FAIL
#SBATCH --mail-user=baolan2005@gmail.com

# ---------------------------------------------------------------------------------------------
# SLURM ARRAY JOB — splits the pairs across array tasks so the batch parallelises across
# bwUniCluster 3.0 "highmem" nodes (96 cores, up to 2,300,000 MB each), not just cores.
#
# Each array task gets its own 96-core node, slices ROWS_PER_TASK pairs out of the full CSV
# (each pair = 500 Monte Carlo rounds, 96 rounds in parallel from configuration.json), and
# passes that slice to a single `java -jar` call, writing pair-named result files so tasks
# never collide. trilemma.jar loops over every row of whatever CSV it's handed itself (it has
# no --row-index flag like atosim.jar), so batching here happens by slicing the CSV per task
# rather than by looping java calls per row within a task (contrast run_selfish.sh in ATOSIM).
#
# ROW_OFFSET/ROWS_PER_TASK follow the same convention as ATOSIM's submit_chunked_array.sh, so
# this script can be driven by it directly (0-indexed SLURM_ARRAY_TASK_ID, offset applied before
# converting to the 1-indexed pair_id column):
#   start = ROW_OFFSET + SLURM_ARRAY_TASK_ID * ROWS_PER_TASK + 1
#   end   = start + ROWS_PER_TASK - 1
#
# ROWS_PER_TASK and the #SBATCH --array default above MUST stay consistent for a direct `sbatch
# run_trilemma_job.sh` submission: array size = ceil(N_PAIRS / ROWS_PER_TASK).
#   The dataset is now nested_trilemma.csv (50,000 pairs), NOT the old 500-row LHS CSV.
#   The #SBATCH --array=0-99 / ROWS_PER_TASK=5 defaults below are sized for the LEGACY 500-row
#   CSV and MUST be recomputed before a full-scale submission against the 50,000-pair CSV
#   (e.g. ROWS_PER_TASK=500 -> --array=0-99 for 500 pairs/task x 500 rounds = 250,000 rounds/task
#   -- re-check the 72h walltime budget below at that size before using it).
# To use a different ROWS_PER_TASK, resume from an offset, or stay under a cluster MaxArraySize,
# submit via:
#   ./submit_chunked_array.sh run_trilemma_job.sh 50000 [chunk_size] [sleep_between] [start_offset] [rows_per_task]
#
# 72h walltime budget per task: rounds/task / 96 cores x per-round-time must be < 72h.
#   e.g. at ~72 min/round: 2500/96 x 72min ~= 31h  (safe). Lower ROWS_PER_TASK if slower.
# ---------------------------------------------------------------------------------------------
ROWS_PER_TASK=${ROWS_PER_TASK:-5}

SUBMIT_DIR="$(pwd)"
FULL_CSV=org.palladiosimulator.blockchainsystems.trilemma/nested_trilemma.csv
TESTMODELS=org.palladiosimulator.blockchainsystems.trilemma/testmodels
BASE_CONFIG=org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json

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

# pair_id range handled by this task
start=$(( ${ROW_OFFSET:-0} + SLURM_ARRAY_TASK_ID * ROWS_PER_TASK + 1 ))
end=$(( start + ROWS_PER_TASK - 1 ))

# Build a per-task CSV slice: header (line 1) + rows whose pair_id (column 1) is in [start,end]
SLICE_CSV="${SUBMIT_DIR}/slice_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.csv"
awk -F',' -v s="$start" -v e="$end" 'NR==1 || ($1 >= s && $1 <= e)' "$FULL_CSV" > "$SLICE_CSV"

echo "Task ${SLURM_ARRAY_TASK_ID}: pairs ${start}-${end} ($(($(wc -l < "$SLICE_CSV") - 1)) rows)"

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

# Single multithreaded JVM on the node's 96 cores. Heap: ~96 rounds x ~3.2 GB ~= 307 GB working
# set, -Xmx900G leaves headroom inside the ~1.13 TB allocation (96 x default 12090 MB/core).
java -Xms256G -Xmx900G \
     -XX:+UseG1GC \
     -XX:ParallelGCThreads=96 \
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
