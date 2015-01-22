package org.salephoto.salephotographicsociety.activity;

import android.util.Log;

import java.util.Date;


public abstract class AbstractEventListFragment extends AbstractListFragment {
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

        if (getListAdapter().getCount() == 0) {
            requestItems();
        } else {
            Log.d(getClass().getSimpleName(), "View already contains "  + getListAdapter().getCount() + " items");
        }
    }

    public abstract void requestItems();

}
