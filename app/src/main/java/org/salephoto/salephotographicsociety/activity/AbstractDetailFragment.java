package org.salephoto.salephotographicsociety.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.squareup.otto.Bus;

import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.bus.BusProvider;


public abstract class AbstractDetailFragment extends Fragment {
    private static final String ARG_INITIAL_ITEM_ID = "currentItemId";

    private int currentItemId = ListView.INVALID_POSITION;
    private RecyclerView viewPager;
    private RecyclerView.LayoutManager layoutManager;
    private Bus bus;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && getArguments().containsKey(ARG_INITIAL_ITEM_ID)) {
            currentItemId = getArguments().getInt(ARG_INITIAL_ITEM_ID);
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        if (currentItemId != ListView.INVALID_POSITION) {
            outState.putInt(ARG_INITIAL_ITEM_ID, currentItemId);
        }
    }

    public int getCurrentItemId() {
        return currentItemId;
    }

    public void setCurrentItemId(final int newItemId) {
        currentItemId = newItemId;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (RecyclerView) view.findViewById(R.id.pager);
        layoutManager = new LinearLayoutManager(getActivity());
        viewPager.setLayoutManager(layoutManager);
    }

    protected void setAdapter(final RecyclerView.Adapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        getBus().unregister(this);
    }

    protected Bus getBus() {
        if (bus == null) {
            bus = BusProvider.getBus();
        }

        return bus;
    }

}
