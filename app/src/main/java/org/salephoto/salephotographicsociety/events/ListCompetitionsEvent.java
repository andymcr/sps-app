package org.salephoto.salephotographicsociety.events;

import android.util.Pair;

import java.util.Date;
import java.util.Set;


public class ListCompetitionsEvent extends AbstractListEventEvent {
    public static final String FIELD_RANK = "rank";
    public static final String FIELD_MAXSCORE = "maxScore";
    public static final String FIELD_HANDINDATE = "handinDate";
    public static final String FIELD_SECRETARY = "secretary";
    public static final String FIELD_SECRETARY_NAME = "secretary.name";
    public static final String FIELD_JUDGES = "judges";
    public static final String FIELD_JUDGE_NAME = "judge.name";
    public static final String FIELD_SECTIONS = "sections";
    public static final String FIELD_SECTION_TITLE = "section.title";

    public ListCompetitionsEvent(final int requesterId) {
        super(requesterId);
    }

    public ListCompetitionsEvent(final int requesterId, final Integer limit,
            final Integer offset, final Pair<String, Order> order) {
        super(requesterId, limit, offset, order);
    }

    public ListCompetitionsEvent(final int requesterId, final Integer limit,
            final Integer offset, final Pair<String, Order> order, final Set<String> fields) {
        super(requesterId, limit, offset, order, fields);
    }

    public ListCompetitionsEvent(final int requesterId, final Date before, final Date after,
            final Integer limit, final Integer offset, final Pair<String, Order> order) {
        super(requesterId, before, after, limit, offset, order);
    }

    public ListCompetitionsEvent(final int requesterId, final Date before, final Date after,
            final Integer limit, final Integer offset, final Pair<String, Order> order,
            final Set<String> fields) {
        super(requesterId, before, after, limit, offset, order, fields);
    }

}
