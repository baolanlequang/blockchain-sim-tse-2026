package org.palladiosimulator.blockchainsystems.core.clock;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.SystemClockControl;

public class SimulationClock implements SystemClockControl {

    private long _currentTime;

    public SimulationClock() {
        _currentTime = 0;
    }

    public void progressClockTo(long newTime) {
        _currentTime = newTime;
    }

    @Override
    public long getCurrentTime() {
        return _currentTime;
    }

}
