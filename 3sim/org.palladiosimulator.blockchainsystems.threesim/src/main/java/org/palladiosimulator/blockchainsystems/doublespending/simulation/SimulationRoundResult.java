package org.palladiosimulator.blockchainsystems.doublespending.simulation;

import java.util.Set;

import org.palladiosimulator.blockchainsystems.doublespending.simulation.termination.SimulationWinnerVote;

public class SimulationRoundResult {

	private final int _numberOfAbstentedVotes;
	private final int _numberOfAttackerWonVotes;
	private final int _numberOfSystemWonVotes;
	private final int _numberOfBTONotIncludedVotes;
	private final int _forkedBlockLength;
	
	public SimulationRoundResult(int numberOfAbstentedVotes, int numberOfAttackerWonVotes, int numberOfSystemWonVotes, int numberOfBTONotIncludedVotes, int forkedBlockLength) {
		_numberOfAbstentedVotes = numberOfAbstentedVotes;
		_numberOfAttackerWonVotes = numberOfAttackerWonVotes;
		_numberOfSystemWonVotes = numberOfSystemWonVotes;
		_numberOfBTONotIncludedVotes = numberOfBTONotIncludedVotes;
		_forkedBlockLength = forkedBlockLength;
	}

	public static SimulationRoundResult create(Set<SimulationWinnerVote> winnerVotes, int forkedBlockLength) {
		int numberOfAbstentedVotes = 0;
		int numberOfAttackerWonVotes = 0;
		int numberOfSystemWonVotes = 0;
		int numberOfBTONotIncludedVotes = 0;
		
		for (SimulationWinnerVote vote : winnerVotes) {
			switch (vote) {
			case NotDetermined:
				numberOfAbstentedVotes++;
				break;
			case AttackerWon:
				numberOfAttackerWonVotes++;
				break;
			case SystemWon:
				numberOfSystemWonVotes++;
				break;
			case BTONotIncluded:
				numberOfBTONotIncludedVotes++;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + vote);
			}
		}
		
		return new SimulationRoundResult(
				numberOfAbstentedVotes,
				numberOfAttackerWonVotes,
				numberOfSystemWonVotes,
				numberOfBTONotIncludedVotes,
				forkedBlockLength);
	}

	public int getNumberOfAbstentedVotes() {
		return _numberOfAbstentedVotes;
	}

	public int getNumberOfAttackerWonVotes() {
		return _numberOfAttackerWonVotes;
	}

	public int getNumberOfSystemWonVotes() {
		return _numberOfSystemWonVotes;
	}

	public int getNumberOfBTONotIncludedVotes() {
		return _numberOfBTONotIncludedVotes;
	}

	public int getLengthOfForkedBlock() {
		return _forkedBlockLength;
	}
}
