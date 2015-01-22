package org.salephoto.salephotographicsociety.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.salephoto.models.Event;
import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.events.AbstractListEvent;
import org.salephoto.salephotographicsociety.events.AbstractListEventEvent;
import org.salephoto.salephotographicsociety.events.EventEvent;
import org.salephoto.salephotographicsociety.events.EventsListedEvent;
import org.salephoto.salephotographicsociety.events.GetEventEvent;
import org.salephoto.salephotographicsociety.events.ListCompetitionsEvent;
import org.salephoto.salephotographicsociety.events.ListEventsEvent;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class EventDetailFragment extends AbstractDetailFragment {
    private static Set<String> fieldsNeeded;

    private EventAdapter adapter;

    public static EventDetailFragment newInstance(final int initialItemId) {
        final EventDetailFragment fragment = new EventDetailFragment();
        fragment.setInitialItemId(initialItemId);

        return fragment;
    }

    public EventDetailFragment() {
        if (fieldsNeeded == null) {
            fieldsNeeded = new HashSet<>();
            fieldsNeeded.add(ListCompetitionsEvent.FIELD_HANDINDATE);
            fieldsNeeded.add(ListCompetitionsEvent.FIELD_SECRETARY);
            fieldsNeeded.add(ListCompetitionsEvent.FIELD_SECRETARY_NAME);
            fieldsNeeded.add(ListCompetitionsEvent.FIELD_JUDGES);
            fieldsNeeded.add(ListCompetitionsEvent.FIELD_JUDGE_NAME);
            fieldsNeeded.add(ListCompetitionsEvent.FIELD_SECTIONS);
        }
    }

    @Override
    public void setInitialItemId(final int initialItemId) {
        super.setInitialItemId(initialItemId);

        getBus().post(new GetEventEvent(initialItemId));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new EventAdapter();
        setAdapter(adapter);
    }

    @Subscribe
    public void onListSeeded(final EventEvent event) {
        adapter.add(0, event.getCompetition());
    }

    @Subscribe
    public void onExtendList(final EventsListedEvent event) {
        if (event.getList().size() > 0) {
            final Event newEvent = event.getList().get(0);
            final Event firstEvent = adapter.first();
            if ((firstEvent == null)
                    || newEvent.getStartTime().before(firstEvent.getStartTime())) {
                adapter.add(0, newEvent);
            } else {
                adapter.add(adapter.getItemCount(), newEvent);
            }
        }
    }

    private class EventAdapter extends DetailAdapter<Event, EventAdapter.ViewHolder> {
        @Override
        public EventAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                final int viewType) {
            final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_detail, parent, false);
            final ViewHolder childViews = new ViewHolder(view);
            childViews.title = (TextView) view.findViewById(R.id.title);
            childViews.date = (TextView) view.findViewById(R.id.date);
            childViews.venue = (TextView) view.findViewById(R.id.venue);

            return childViews;
        }

        @Override
        public void onBindViewHolder(final ViewHolder childViews, final int position) {
            final Event event = get(position);
            childViews.title.setText(event.getTitle());
            childViews.date.setText(formatDate(event.getStartTime()));
            childViews.venue.setText("");
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView date;
            public TextView title;
            public TextView venue;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public void requestPrevious(final Date date) {
            getBus().post(new ListEventsEvent(ListView.INVALID_POSITION, date, null, 1, 0,
                new Pair<>(AbstractListEventEvent.FIELD_DATE, AbstractListEvent.Order.Descending),
                fieldsNeeded));
        }

        @Override
        public void requestNext(final Date date) {
            getBus().post(new ListEventsEvent(ListView.INVALID_POSITION, null, date, 1, 0,
                new Pair<>(AbstractListEventEvent.FIELD_DATE, AbstractListEvent.Order.Ascending),
                fieldsNeeded));
        }
    }
}
