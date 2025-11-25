package org.palladiosimulator.blockchainsystems.core.eventcoordination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.palladiosimulator.blockchainsystems.core.common.abstractions.Event;

public class EffectsTimeSlice {

    private final long _timestamp;
    private final ArrayList<Event> _events;

    public EffectsTimeSlice(
            long timestamp) {
        _timestamp = timestamp;
        _events = new ArrayList<Event>();
    }

    public long getTimestamp() {
        return _timestamp;
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(_events);
    }

    public void addEvent(Event event) {
        _events.add(event);
    }

    public void removeEvent(Event event) {
        _events.remove(event);
    }
}
