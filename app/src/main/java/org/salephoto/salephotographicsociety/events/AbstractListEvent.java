package org.salephoto.salephotographicsociety.events;

import android.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


abstract public class AbstractListEvent extends AbstractEvent {
    private Integer limit;
    private Integer offset;
    private Map<String, Order> order = new HashMap<>();

    public AbstractListEvent() {
    }

    public AbstractListEvent(final Integer limit, final Integer offset,
            final Pair<String, Order> order) {
        init(limit, offset, order);
    }

    public AbstractListEvent(final Integer limit, final Integer offset,
            final Pair<String, Order> order, final Set<String> fields) {
        super(fields);
        init(limit, offset, order);
    }

    public void init(final Integer limit, final Integer offset, final Pair<String, Order> order) {
        this.limit = limit;
        this.offset = offset;
        addOrder(order);
    }

    public Integer getLimit() {
        if (limit > 0) {
            return limit;
        } else {
            return null;
        }
    }

    public Integer getOffset() {
        return offset;
    }

    public void addOrder(final Pair<String, Order> order) {
        if (order != null) {
            this.order.put(order.first, order.second);
        }
    }

    public Map<String, Order> getOrder() {
        return order;
    }

    public enum Order {
        Ascending(), Descending()
    }

}
