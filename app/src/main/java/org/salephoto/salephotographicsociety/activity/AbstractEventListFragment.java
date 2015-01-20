package org.salephoto.salephotographicsociety.activity;

import org.salephoto.salephotographicsociety.events.ListEventEvent;

import java.util.Date;


public class AbstractEventListFragment<T> extends AbstractListFragment {
    public static final String ARG_BEFORE_DATE  = "before";
    public static final String ARG_AFTER_DATE = "after";

    private Date before;
    private Date after;


    public Date getBefore() {
        return before;
    }

    public Date getAfter() {
        return after;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getArguments().containsKey(ARG_BEFORE_DATE)) {
            before = (Date) getArguments().getSerializable(ARG_BEFORE_DATE);
        }
        if (getArguments().containsKey(ARG_AFTER_DATE)) {
            after = (Date) getArguments().getSerializable(ARG_AFTER_DATE);
        }

        requestItems();
    }

    public void requestItems() {
        getBus().post(new ListEventEvent<T>(getArguments().getInt(ARG_ID),
            getBefore(), getAfter(), 5, getListAdapter().getCount(), getOrder()));
    }

}
