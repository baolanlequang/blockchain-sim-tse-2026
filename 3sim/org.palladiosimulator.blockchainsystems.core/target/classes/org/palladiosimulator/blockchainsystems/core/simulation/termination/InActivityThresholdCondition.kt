package org.palladiosimulator.blockchainsystems.core.simulation.termination

import org.palladiosimulator.blockchainsystems.core.clock.SimulationClock
import kotlin.math.ln

class InActivityThresholdCondition (
    private val blockCreationInterval: Double
) {
    var simulationClock: SimulationClock? = null

    var lastLoggedSimulationClock: Long = simulationClock?.currentTime ?: 0
        private set

//    val inactivityThreshold =  -(blockCreationInterval * ln(0.01))
    val inactivityThreshold = blockCreationInterval + 1800000  // BCI + 30 mins

    fun restartLoggedSimulationClock() {
        lastLoggedSimulationClock = simulationClock?.currentTime ?: 0
    }

    fun hasProlongedInactivityExceeded(): Boolean {
        val inactivityTime = (simulationClock?.currentTime ?: 0) - lastLoggedSimulationClock
//        println("blockCreationInterval: " + blockCreationInterval)
//        println("inactivityThreshold: " + inactivityThreshold)
//        println("inactivateTime: " + inactivityTime)
//        println("hasProlongedInactivityExceeded: " + (inactivityTime > inactivityThreshold))
        return inactivityTime > inactivityThreshold
    }
}