package org.salephoto.salephotographicsociety.repository;

public enum EventOrder {
    UNSPECIFIED(null),
    DATE_ASCENDING("dateAscending"),
    DATE_DESCENDING("dateDescending");

    private String queryValue;

    private EventOrder(final String queryValue) {
        this.queryValue = queryValue;
    }

    public String queryValue() {
        return queryValue;
    }

}
