#!/bin/bash
#SBATCH --job-name=run_selfishmining_job
#SBATCH --output=logs/selfish_%A_%a.out
#SBATCH --error=logs/selfish_%A_%a.err
#SBATCH --partition=highmem
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=96
#SBATCH --time=72:00:00
#SBATCH --array=0-99
#SBATCH --mail-type=BEGIN,END,FAIL
#SBATCH --mail-user=baolan2005@gmail.com

# ---------------------------------------------------------------------------------------------
# SLURM ARRAY JOB — splits the 500 configs across array tasks so the batch parallelises across
# bwUniCluster 3.0 "highmem" nodes (96 cores, up to 2,300,000 MB each), not just cores.
#
# Each array task gets its own 96-core node, slices ROWS_PER_TASK configs out of the full CSV
# (each config = 500 Monte Carlo rounds, 96 rounds in parallel from configuration.json), and
# passes that slice to a single `java -jar` call, writing config-named result files so tasks
# never collide. selfishmining.jar loops over every row of whatever CSV it's handed itself (it
# has no --row-index flag like atosim.jar), so batching here happens by slicing the CSV per task
# rather than by looping java calls per row within a task (contrast run_selfish.sh in ATOSIM).
#
# ROW_OFFSET/ROWS_PER_TASK follow the same convention as ATOSIM's submit_chunked_array.sh, so
# this script can be driven by it directly (0-indexed SLURM_ARRAY_TASK_ID, offset applied before
# converting to the 1-indexed config_id column):
#   start = ROW_OFFSET + SLURM_ARRAY_TASK_ID * ROWS_PER_TASK + 1
#   end   = start + ROWS_PER_TASK - 1
#
# ROWS_PER_TASK and the #SBATCH --array default above MUST stay consistent for a direct `sbatch
# run_selfish_job.sh` submission: array size = ceil(500 / ROWS_PER_TASK).
#   ROWS_PER_TASK=5  -> --array=0-99   (5 configs x 500 rounds = 2500 rounds/task)
#   ROWS_PER_TASK=10 -> --array=0-49
# To use a different ROWS_PER_TASK, resume from an offset, or stay under a cluster MaxArraySize,
# submit via:
#   ./submit_chunked_array.sh run_selfish_job.sh 500 [chunk_size] [sleep_between] [start_offset] [rows_per_task]
#
# 72h walltime budget per task: rounds/task / 96 cores x per-round-time must be < 72h.
#   e.g. at ~72 min/round: 2500/96 x 72min ~= 31h  (safe). Lower ROWS_PER_TASK if slower.
# ---------------------------------------------------------------------------------------------
ROWS_PER_TASK=${ROWS_PER_TASK:-5}

FULL_CSV=org.palladiosimulator.blockchainsystems.trilemma/optimized_selfish.csv
TESTMODELS=org.palladiosimulator.blockchainsystems.trilemma/testmodels
BASE_CONFIG=org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json

mkdir -p logs result_selfishmining

# config_id range handled by this task
start=$(( ${ROW_OFFSET:-0} + SLURM_ARRAY_TASK_ID * ROWS_PER_TASK + 1 ))
end=$(( start + ROWS_PER_TASK - 1 ))

# Build a per-task CSV slice: header (line 1) + rows whose config_id (column 1) is in [start,end]
SLICE_CSV=slice_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.csv
awk -F',' -v s="$start" -v e="$end" 'NR==1 || ($1 >= s && $1 <= e)' "$FULL_CSV" > "$SLICE_CSV"

echo "Task ${SLURM_ARRAY_TASK_ID}: configs ${start}-${end} ($(($(wc -l < "$SLICE_CSV") - 1)) rows)"

# Single multithreaded JVM on the node's 96 cores. Heap: ~96 rounds x ~3.2 GB ~= 307 GB working
# set, -Xmx900G leaves headroom inside the ~1.13 TB allocation (96 x default 12090 MB/core).
java -Xms256G -Xmx900G \
     -XX:+UseG1GC \
     -XX:ParallelGCThreads=96 \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=heapdump_${SLURM_ARRAY_JOB_ID}_${SLURM_ARRAY_TASK_ID}.hprof \
     -jar selfishmining.jar \
     "$SLICE_CSV" \
     "$TESTMODELS" \
     "$BASE_CONFIG"

rm -f "$SLICE_CSV"
