package org.salephoto.salephotographicsociety.events;

import android.util.Pair;

import java.util.Date;
import java.util.Set;


public class ListEventsEvent extends AbstractListEventEvent {
    public ListEventsEvent(final int requesterId) {
        super(requesterId);
    }

    public ListEventsEvent(final int requesterId, final Integer limit,
            final Integer offset, final Pair<String, Order> order) {
        super(requesterId, limit, offset, order);
    }

    public ListEventsEvent(final int requesterId, final Integer limit,
            final Integer offset, final Pair<String, Order> order, final Set<String> fields) {
        super(requesterId, limit, offset, order, fields);
    }

    public ListEventsEvent(final int requesterId, final Date before, final Date after,
            final Integer limit, final Integer offset, final Pair<String, Order> order) {
        super(requesterId, before, after, limit, offset, order);
    }

    public ListEventsEvent(final int requesterId, final Date before, final Date after,
            final Integer limit, final Integer offset, final Pair<String, Order> order,
            final Set<String> fields) {
        super(requesterId, before, after, limit, offset, order, fields);
    }

}
