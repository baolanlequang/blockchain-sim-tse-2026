#!/bin/bash
#SBATCH --job-name=run_trilemma_job
#SBATCH --output=result_trilemma_%j.out
#SBATCH --error=result_trilemma_%j.err
#SBATCH --partition=highmem
#SBATCH --nodes=1
#SBATCH --ntasks=1
#SBATCH --cpus-per-task=48
#SBATCH --mem-per-cpu=12090mb
#SBATCH --time=72:00:00
#SBATCH --mail-type=BEGIN,END,FAIL
#SBATCH --mail-user=baolan2005@gmail.com

java -Xmx450G \
     -XX:+UseG1GC \
     -XX:ParallelGCThreads=48 \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=heapdump_${SLURM_JOB_ID}.hprof \
     -jar trilemma.jar \
     org.palladiosimulator.blockchainsystems.trilemma/optimized_trilemma.csv \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json