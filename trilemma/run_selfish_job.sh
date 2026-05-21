#!/bin/bash
#SBATCH --job-name=run_selfishmining_job
#SBATCH --output=result_%j.out
#SBATCH --error=result_%j.err
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=24
#SBATCH --mem=500000
#SBATCH --time=72:00:00
#SBATCH --partition=highmem

java -Xms400G -Xmx400G \
     -XX:+UseG1GC \
     -XX:ParallelGCThreads=24 \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=heapdump_${SLURM_JOB_ID}.hprof \
     -jar selfishmining.jar \
     org.palladiosimulator.blockchainsystems.trilemma/optimized_deterministic_lhs_configurations.csv \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json