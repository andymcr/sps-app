package org.salephoto.salephotographicsociety.events;

import org.salephoto.models.Competition;

public class CompetitionUpdatedEvent {
    private Competition competition;

    public CompetitionUpdatedEvent(final Competition competition) {
        this.competition = competition;
    }

    public Competition getCompetition() {
        return competition;
    }
}
