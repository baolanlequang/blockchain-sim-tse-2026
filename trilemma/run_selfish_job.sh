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

java -Xmx450G \
     -XX:+UseG1GC \
     -XX:ParallelGCThreads=48 \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=heapdump_${SLURM_JOB_ID}.hprof \
     -jar selfishmining.jar \
     org.palladiosimulator.blockchainsystems.trilemma/optimized_deterministic_lhs_configurations.csv \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json