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
#SBATCH --mail-type=BEGIN,END,FAIL
#SBATCH --mail-user=baolan2005@gmail.com

# highmem half-node: 48 cores, 48 x 12090 MB = ~566 GB allocated.
# configuration.json sets numberOfParallelTasks=48 (one round per allocated core);
# 48 concurrent MC rounds x ~3.2 GB/round = ~154 GB peak heap — well within 566 GB.
# Running more threads than cores (e.g. the old default of 96) only adds context-
# switching overhead for this CPU-bound workload, so we match the core count.
# -Xmx450G caps the JVM safely under the 566 GB budget.
java -Xmx450G \
     -XX:+UseG1GC \
     -XX:ParallelGCThreads=48 \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=heapdump_${SLURM_JOB_ID}.hprof \
     -jar selfishmining.jar \
     org.palladiosimulator.blockchainsystems.trilemma/optimized_selfish.csv \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels \
     org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json
