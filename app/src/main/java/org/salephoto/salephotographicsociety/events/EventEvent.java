package org.salephoto.salephotographicsociety.events;

import org.salephoto.models.Event;


public class EventEvent {
    private Event event;

    public EventEvent(final Event event) {
        this.event = event;
    }

    public Event getCompetition() {
        return event;
    }

}
