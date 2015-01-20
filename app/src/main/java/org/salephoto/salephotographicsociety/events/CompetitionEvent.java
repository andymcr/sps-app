package org.salephoto.salephotographicsociety.events;

import org.salephoto.models.Competition;


public class CompetitionEvent {
    private Competition competition;

    public CompetitionEvent(final Competition competition) {
        this.competition = competition;
    }

    public Competition getCompetition() {
        return competition;
    }

}
