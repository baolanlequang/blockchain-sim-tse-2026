#!/bin/bash
#SBATCH --job-name=run_selfishmining_job
#SBATCH --output=result_%j.out
#SBATCH --error=result_%j.err
#SBATCH --partition=highmem
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=96
#SBATCH --mem-per-cpu=12090mb
#SBATCH --time=72:00:00

# highmem node: 96 cores, 96 x 12090 MB = ~1.13 TB allocated.
# 96 concurrent MC rounds x ~3.2 GB/round = ~307 GB peak heap usage.
# -Xmx1000G caps the JVM well under the 1.13 TB budget.
java -Xmx1000G \
     -XX:+UseG1GC \
     -XX:ParallelGCThreads=96 \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=heapdump_${SLURM_JOB_ID}.hprof \
     -jar selfishmining.jar \
     org.palladiosimulator.blockchainsystems.trilemma/optimized_deterministic_lhs_configurations.csv \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json