#!/bin/bash
#SBATCH --job-name=run_selfishmining_job  # Name of the job
#SBATCH --output=result_%j.out            # Standard output log (%j inserts JobID)
#SBATCH --error=result_%j.err             # Error log
#SBATCH --nodes=1                        # Number of nodes
#SBATCH --ntasks=1                        # Number of tasks
#SBATCH --cpus-per-task=20                # Number of CPU cores per task
#SBATCH --mem=2300000                      # Total memory (RAM)
#SBATCH --time=72:00:00                   # Time limit (HH:MM:SS)
#SBATCH --partition=highmem                   # Partition/Queue name

java -Xmx600G -XX:ParallelGCThreads=20 -jar selfishmining.jar org.palladiosimulator.blockchainsystems.trilemma/optimized_deterministic_lhs_configurations.csv org.palladiosimulator.blockchainsystems.trilemma/testmodels org.palladiosimulator.blockchainsystems.trilemma/testmodels/configuration.json