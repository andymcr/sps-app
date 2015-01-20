package org.salephoto.salephotographicsociety.events;

import android.util.Pair;

import java.util.HashMap;
import java.util.Map;


abstract public class AbstractListEvent {
    private Integer limit;
    private Integer offset;
    private Map<String, Order> order = new HashMap<>();

    public AbstractListEvent() {
    }

    public AbstractListEvent(final Integer limit, final Integer offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public AbstractListEvent(final Integer limit, final Integer offset, final Pair<String, Order> order) {
        this(limit, offset);
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
