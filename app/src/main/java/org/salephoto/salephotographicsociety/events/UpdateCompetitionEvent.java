package org.salephoto.salephotographicsociety.events;

import org.salephoto.models.Competition;

public class UpdateCompetitionEvent extends AbstractEvent {
    private Competition competition;

    public UpdateCompetitionEvent(final Competition competition) {
        this.competition = competition;
    }

    public Competition getCompetition() {
        return competition;
    }
}
