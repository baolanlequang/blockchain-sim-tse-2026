#!/bin/bash
#SBATCH --job-name=run_selfishmining_job
#SBATCH --output=result_%j.out
#SBATCH --error=result_%j.err
#SBATCH --partition=highmem
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=48
#SBATCH --mem-per-cpu=12090mb
#SBATCH --time=72:00:00

# Always run from the directory containing this script and selfishmining.jar
cd "$(dirname "$(realpath "$0")")"

# highmem half-node: 48 cores, 48 x 12090 MB = ~566 GB allocated.
# 96 concurrent MC rounds x ~3.2 GB/round = ~307 GB peak heap — fits within 566 GB.
# -Xmx450G caps the JVM safely under the 566 GB budget.
java -Xmx450G \
     -XX:+UseG1GC \
     -XX:ParallelGCThreads=48 \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=heapdump_${SLURM_JOB_ID}.hprof \
     -jar selfishmining.jar \
     org.palladiosimulator.blockchainsystems.trilemma/optimized_deterministic_lhs_configurations.csv \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json
