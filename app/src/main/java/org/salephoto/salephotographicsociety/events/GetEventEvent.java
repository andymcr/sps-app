package org.salephoto.salephotographicsociety.events;


public class GetEventEvent extends AbstractEvent {
    private int eventId;


    public GetEventEvent(final int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }
}
