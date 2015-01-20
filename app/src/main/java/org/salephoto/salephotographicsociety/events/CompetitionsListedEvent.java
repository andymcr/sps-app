package org.salephoto.salephotographicsociety.events;

import org.salephoto.models.Competition;

import java.util.List;


public class CompetitionsListedEvent extends ListedEvent<Competition> {
    public CompetitionsListedEvent(final int requesterId, final List<Competition> list) {
        super(requesterId, list);

    }
}
