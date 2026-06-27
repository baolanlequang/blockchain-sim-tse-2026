#!/bin/bash
#SBATCH --job-name=run_trilemma_job
#SBATCH --output=logs/trilemma_%A_%a.out
#SBATCH --error=logs/trilemma_%A_%a.err
#SBATCH --partition=highmem
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=96
#SBATCH --time=72:00:00
#SBATCH --array=1-100
#SBATCH --mail-type=BEGIN,END,FAIL
#SBATCH --mail-user=baolan2005@gmail.com

# ---------------------------------------------------------------------------------------------
# SLURM ARRAY JOB — splits the 500 configs across array tasks so the batch parallelises across
# bwUniCluster 3.0 "highmem" nodes (96 cores, up to 2,300,000 MB each), not just cores.
#
# Each array task gets its own 96-core node, runs CONFIGS_PER_TASK configs (each = 500 Monte
# Carlo rounds, 96 rounds in parallel from configuration.json), and writes config-named result
# files so tasks never collide.
#
# CONFIGS_PER_TASK and --array MUST stay consistent: array size = ceil(500 / CONFIGS_PER_TASK).
#   CONFIGS_PER_TASK=5  -> --array=1-100   (5 configs x 500 rounds = 2500 rounds/task)
#   CONFIGS_PER_TASK=10 -> --array=1-50
# 72h walltime budget per task: rounds/task / 96 cores x per-round-time must be < 72h.
#   e.g. at ~72 min/round: 2500/96 x 72min ~= 31h  (safe). Lower CONFIGS_PER_TASK if slower.
# ---------------------------------------------------------------------------------------------
CONFIGS_PER_TASK=5

FULL_CSV=org.palladiosimulator.blockchainsystems.trilemma/optimized_trilemma.csv
TESTMODELS=org.palladiosimulator.blockchainsystems.trilemma/testmodels
BASE_CONFIG=org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json

mkdir -p logs result_trilemma

# config_id range handled by this task
start=$(( (SLURM_ARRAY_TASK_ID - 1) * CONFIGS_PER_TASK + 1 ))
end=$(( SLURM_ARRAY_TASK_ID * CONFIGS_PER_TASK ))

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
     -jar trilemma.jar \
     "$SLICE_CSV" \
     "$TESTMODELS" \
     "$BASE_CONFIG"

rm -f "$SLICE_CSV"
