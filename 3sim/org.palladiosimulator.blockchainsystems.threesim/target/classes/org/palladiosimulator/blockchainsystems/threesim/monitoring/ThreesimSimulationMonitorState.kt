package org.palladiosimulator.blockchainsystems.threesim.monitoring

class ThreesimSimulationMonitorState(
  val numberOfNodes: Int,
  val hashPowerPerNode: Collection<Double>,
  val blocksProposedPerNode: Collection<Int>,
  val numberOfGeographicalRegions: Int,
  val numberOfNodesPerRegion: Collection<Int>,
  val numberOfSubmittedTransactions: Int,
  val numberOfConfirmedTransactions: Int,
  val tokensHeldPerNode: List<Double>,
  val blockProposalTimeAndConfirmationTimePerConfirmedBlock: Collection<Pair<Long, Long>>,
  val meanTimeBetweenFailures: Double,
  val meanTimeToRepair: Double,
  val numberOfStaleBlocks: Int,
  val numberOfConfirmedBlocks: Int,
  val averageThroughputDuringFailure: Double,
  val averageThroughputDuringNormalOperation: Double,
  val averageConfirmationLatencyDuringFailure: Double,
  val averageConfirmationLatencyDuringNormalOperation: Double
)