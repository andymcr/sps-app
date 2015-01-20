package org.salephoto.salephotographicsociety.events;

import android.util.Pair;

import java.util.Date;


public class ListEventEvent<T> extends AbstractListEvent {
    public final static String FIELD_DATE = "date";

    private int requesterId;
    private Date before;
    private Date after;

    public ListEventEvent(final int requesterId) {
        this.requesterId = requesterId;
    }

    public ListEventEvent(final int requesterId, final Integer limit, final Integer offset) {
        super(limit, offset);
        this.requesterId = requesterId;
    }

    public ListEventEvent(final int requesterId, final Integer limit,
            final Integer offset, final Pair<String, Order> order) {
        super(limit, offset, order);
        this.requesterId = requesterId;
    }

    public ListEventEvent(final int requesterId, final Date before, final Date after,
            final Integer limit, final Integer offset) {
        super(limit, offset);
        this.requesterId = requesterId;
        this.before = before;
        this.after = after;
    }

    public ListEventEvent(final int requesterId, final Date before, final Date after,
            final Integer limit, final Integer offset, final Pair<String, Order> order) {
        super(limit, offset, order);
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
