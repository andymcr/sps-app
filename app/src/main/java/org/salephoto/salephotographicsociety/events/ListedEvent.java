package org.salephoto.salephotographicsociety.events;

import java.util.List;


public class ListedEvent<T> {
    private int requesterId;
    private List<T> list;

    public ListedEvent(final  int requesterId, final List<T> list) {
        this.requesterId= requesterId;
        this.list = list;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public List<T> getList() {
        return list;
    }

}
