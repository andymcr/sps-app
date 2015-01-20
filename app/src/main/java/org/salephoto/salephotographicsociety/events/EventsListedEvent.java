package org.salephoto.salephotographicsociety.events;

import org.salephoto.models.Event;

import java.util.List;


public class EventsListedEvent extends ListedEvent<Event> {
    public EventsListedEvent(final int requesterId, final List<Event> list) {
        super(requesterId, list);

    }
}
