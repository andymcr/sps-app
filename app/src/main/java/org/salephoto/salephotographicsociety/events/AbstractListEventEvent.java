package org.salephoto.salephotographicsociety.events;

import android.util.Pair;

import java.util.Date;
import java.util.Set;


public abstract class AbstractListEventEvent extends AbstractListEvent {
    public static final String FIELD_DATE = "date";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_STARTTIME = "startTime";
    public static final String FIELD_DURATION = "duration";
    public static final String FIELD_VENUE = "venue";

    private int requesterId;
    private Date before;
    private Date after;

    public AbstractListEventEvent(final int requesterId) {
        this.requesterId = requesterId;
    }

    public AbstractListEventEvent(final int requesterId, final Integer limit,
            final Integer offset, final Pair<String, Order> order) {
        super(limit, offset, order);
        init(requesterId, null, null);
    }

    public AbstractListEventEvent(final int requesterId, final Integer limit,
            final Integer offset, final Pair<String, Order> order, final Set<String>fields) {
        super(limit, offset, order, fields);
        init(requesterId, null, null);
    }

    public AbstractListEventEvent(final int requesterId, final Date before, final Date after,
            final Integer limit, final Integer offset, final Pair<String, Order> order) {
        super(limit, offset, order);
        init(requesterId, before, after);
    }

    public AbstractListEventEvent(final int requesterId, final Date before, final Date after,
            final Integer limit, final Integer offset, final Pair<String, Order> order,
            final Set<String> fields) {
        super(limit, offset, order, fields);
        init(requesterId, before, after);
    }

    private void init(final int requesterId, final Date before, final Date after) {
        this.requesterId = requesterId;
        this.before = before;
        this.after = after;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public Date getBefore() {
        return before;
    }

    public Date getAfter() {
        return after;
    }

}
