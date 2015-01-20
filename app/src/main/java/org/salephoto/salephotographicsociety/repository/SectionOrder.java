package org.salephoto.salephotographicsociety.repository;

public enum SectionOrder {
    UNSPECIFIED(null),
    ORDER_ASCENDING("orderAscending"),
    ORDER_DESCENDING("orderDescending"),
    TITLE_ASCENDING("titleAscending"),
    TITLE_DESCENDING("titleDescending");

    private String queryValue;

    private SectionOrder(final String queryValue) {
        this.queryValue = queryValue;
    }

    public String queryValue() {
        return queryValue;
    }

}
