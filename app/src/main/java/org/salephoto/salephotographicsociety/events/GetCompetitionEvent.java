package org.salephoto.salephotographicsociety.events;

import org.salephoto.salephotographicsociety.repository.EntryOrder;
import org.salephoto.salephotographicsociety.repository.SectionOrder;


public class GetCompetitionEvent extends AbstractGetEvent {
    private int competitionId;
    private SectionOrder sectionOrder = SectionOrder.UNSPECIFIED;
    private EntryOrder entryOrder = EntryOrder.UNSPECIFIED;


    public GetCompetitionEvent(final int competitionId) {
        this.competitionId = competitionId;
    }

    public GetCompetitionEvent(int competitionId, EntryOrder entryOrder) {
        this.competitionId = competitionId;
        this.entryOrder = entryOrder;
    }

    public GetCompetitionEvent(int competitionId, SectionOrder sectionOrder, EntryOrder entryOrder) {
        this.competitionId = competitionId;
        this.sectionOrder = sectionOrder;
        this.entryOrder = entryOrder;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public SectionOrder getSectionOrder() {
        return sectionOrder;
    }

    public EntryOrder getEntryOrder() {
        return entryOrder;
    }
}
