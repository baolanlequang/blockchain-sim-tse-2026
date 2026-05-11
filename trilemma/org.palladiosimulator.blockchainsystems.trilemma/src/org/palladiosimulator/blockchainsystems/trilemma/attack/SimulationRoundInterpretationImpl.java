package org.palladiosimulator.blockchainsystems.trilemma.attack;

import org.palladiosimulator.blockchainsystems.doublespending.simulation.InterpretedResult;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.SimulationRoundInterpretation;
import org.palladiosimulator.blockchainsystems.doublespending.simulation.SimulationRoundResult;

public class SimulationRoundInterpretationImpl implements SimulationRoundInterpretation {

	public InterpretedResult interpretRoundResult(SimulationRoundResult roundResult) {
		
//		System.out.println("roundResult: " + roundResult.getLengthOfForkedBlock());
		
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
	
	private static boolean areVotesUnambiguous(SimulationRoundResult roundResult) {
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

}
