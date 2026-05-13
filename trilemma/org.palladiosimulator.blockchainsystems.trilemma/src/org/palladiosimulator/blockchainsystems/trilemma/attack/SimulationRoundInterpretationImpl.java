package org.palladiosimulator.blockchainsystems.trilemma.attack;

import org.palladiosimulator.blockchainsystems.doublespending.simulation.InterpretedResult;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.SimulationRoundInterpretation;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.SimulationRoundResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.palladiosimulator.blockchainsystems.doublespending.simulation.DoubleSpendingSimulationRoundResult;

public class SimulationRoundInterpretationImpl implements SimulationRoundInterpretation {
	
	private int runId;
	
	public SimulationRoundInterpretationImpl(int runId) {
		this.runId = runId;
	}

	@Override
	public InterpretedResult interpretRoundResult(DoubleSpendingSimulationRoundResult roundResult) {
		
		saveLogs(roundResult);
		
		if (areVotesUnambiguous(roundResult)) {
			return InterpretedResult.Unambiguous;
		}
		
		if (roundResult.getNumberOfAttackerWonVotes() > 0) {
			return InterpretedResult.AttackerWon;
		}
		
		if (roundResult.getNumberOfSystemWonVotes() > 0) {
			return InterpretedResult.SystemWon;
		}
		
		/*if (roundResult.getNumberOfBTONotIncludedVotes() > 0) {
			
		}*/
		
		return InterpretedResult.Unambiguous;
	}
	
	private static boolean areVotesUnambiguous(DoubleSpendingSimulationRoundResult roundResult) {
		int numberOfVoteTypesGreaterThanZero = 0;
		
		if (roundResult.getNumberOfAttackerWonVotes() > 0) {
			numberOfVoteTypesGreaterThanZero++;
		}
		
		if (roundResult.getNumberOfBTONotIncludedVotes() > 0) {
			numberOfVoteTypesGreaterThanZero++;
		}
		
		if (roundResult.getNumberOfSystemWonVotes() > 0) {
			numberOfVoteTypesGreaterThanZero++;
		}
		
		return numberOfVoteTypesGreaterThanZero != 1;
	}

	private void saveLogs(DoubleSpendingSimulationRoundResult roundResult) {
		try {
            // Create directory if it does not exist
            File directory = new File("selfish_log");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Write to file
            String fileName = String.format("selfish_log/log_for_run_%d.txt", runId);
            try (PrintWriter writer =
                     new PrintWriter(new FileWriter(fileName, true))) {

                writer.println("Forked length: " + roundResult.getLengthOfForkedBlock());
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}


}
