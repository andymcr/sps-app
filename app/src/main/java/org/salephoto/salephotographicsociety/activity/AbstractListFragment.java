package org.salephoto.salephotographicsociety.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.otto.Bus;

import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.bus.BusProvider;
import org.salephoto.salephotographicsociety.events.AbstractListEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;


abstract public class AbstractListFragment extends ListFragment {
    private static final String ACTIVATED_POSITION = "activatedPosition";
    public static final String ARG_ID = "id";
    public static final String ARG_ORDER_FIELD = "orderField";
    public static final String ARG_ORDER_DIRECTION = "orderDirection";

    private String title;
    private int activatedPosition = ListView.INVALID_POSITION;
    private Pair<String, AbstractListEvent.Order> order;
    private Bus bus;


    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        if ((savedInstanceState != null) && savedInstanceState.containsKey(ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(ACTIVATED_POSITION));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (activatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(ACTIVATED_POSITION, activatedPosition);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getArguments().containsKey(ARG_ORDER_FIELD)
                && getArguments().containsKey(ARG_ORDER_DIRECTION)) {
            order = new Pair<>(getArguments().getString(ARG_ORDER_FIELD),
                AbstractListEvent.Order.valueOf(getArguments().getString(ARG_ORDER_DIRECTION)));
        }

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

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        setActivatedPosition(position);
    }

    protected void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(activatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }
        activatedPosition = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String newTitle) {
        title = newTitle;
    }

    public Pair<String, AbstractListEvent.Order> getOrder() {
        return order;
    }

    public abstract class ListAdapter<T> extends ArrayAdapter<T> {
        private SimpleDateFormat dateFormatter
            = new SimpleDateFormat(getResources().getString(R.string.summary_date_pattern));

        private boolean listComplete;

        public ListAdapter(final Context context) {
            super(context, 0);
            setListShown(false);
        }

        @Override
        public void addAll(final Collection<? extends T> collection) {
            if (collection.size() > 0) {
                setListShown(true);
                super.addAll(collection);
            } else {
                 listComplete = true;
            }
        }

        public DateFormat getDateFormatter() {
            return dateFormatter;
        }

        public boolean isListComplete() {
            return listComplete;
        }

    }

}
