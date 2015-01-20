package org.salephoto.salephotographicsociety.events;

import org.salephoto.models.Talk;

import java.util.List;


public class TalksListedEvent extends ListedEvent<Talk> {
    public TalksListedEvent(final int requesterId, final List<Talk> list) {
        super(requesterId, list);

    }
}
