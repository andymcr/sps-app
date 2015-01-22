package org.salephoto.salephotographicsociety.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.otto.Bus;

import org.salephoto.models.EventCore;
import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.bus.BusProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public abstract class AbstractDetailFragment extends Fragment {
    private static final String ARG_INITIAL_ITEM_ID = "currentItemId";

    private int currentItemId = ListView.INVALID_POSITION;
    private RecyclerView viewPager;
    private Bus bus;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && (getArguments() != null)
                && getArguments().containsKey(ARG_INITIAL_ITEM_ID)) {
            currentItemId = getArguments().getInt(ARG_INITIAL_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.details_list, container, false);

        viewPager = (RecyclerView) view.findViewById(R.id.pager);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        viewPager.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        return view;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        if (currentItemId != ListView.INVALID_POSITION) {
            outState.putInt(ARG_INITIAL_ITEM_ID, currentItemId);
        }
    }

    protected void setInitialItemId(final int initialItemId) {
        currentItemId = initialItemId;
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

    protected abstract class DetailAdapter<S extends EventCore,
            T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
        private List<S> items = new ArrayList<>();
        private SimpleDateFormat dateFormat
                = new SimpleDateFormat(getResources().getString(R.string.summary_date_pattern));

        public String formatDate(final Date date) {
            return dateFormat.format(date);
        }

        public S first() {
            if (items.size() > 0) {
                return items.get(0);
            } else {
                return null;
            }
        }

        public void add(final int position, final S item) {
            items.add(position, item);
            notifyItemInserted(position);
        }

        public S get(final int position) {
            if (items.size() > 0) {
                if (position == 0) {
                    requestPrevious(items.get(0).getStartTime());
                }
                if (position == items.size() - 1) {
                    requestNext(items.get(items.size() - 1).getStartTime());
                }
            }

            return items.get(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public abstract void requestPrevious(final Date date);

        public abstract void requestNext(final Date date);

        public void update(final S item) {
            notifyItemChanged(items.indexOf(item));
        }
    }

}
