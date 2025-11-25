package org.palladiosimulator.blockchainsystems.core.common.abstractions;

public interface SystemClockControl extends SystemClock {

	void progressClockTo(long newClockTime);
}
