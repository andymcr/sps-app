package org.salephoto.salephotographicsociety.repository;

public enum EntryOrder {
    UNSPECIFIED(null),
    AWARD_ASCENDING("awardAscending"),
    AWARD_DESCENDING("awardDescending"),
    TITLE_ASCENDING("titleAscending"),
    TITLE_DESCENDING("titleDescending");

    private String queryValue;

    private EntryOrder(final String queryValue) {
        this.queryValue = queryValue;
    }

    public String queryValue() {
        return queryValue;
    }

}
