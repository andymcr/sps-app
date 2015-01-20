package org.salephoto.salephotographicsociety.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.salephoto.models.Event;
import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.events.EventsListedEvent;


public class EventListFragment extends AbstractEventListFragment {
    private EventListAdapter adapter;

    public interface EventListListener {
        void onEventSelected(int eventId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new EventListAdapter(getActivity());
        setListAdapter(adapter);
    }

    @Subscribe
    public void onEventsLoaded(EventsListedEvent event) {
        if (event.getRequesterId() == getArguments().getInt(ARG_ID)) {
            adapter.addAll(event.getList());
        }
    }

    private class EventListAdapter extends ListAdapter<Event> {
        public EventListAdapter(final Context context) {
            super(context);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            if (!isListComplete() && ((position + 1) == this.getCount())) {
                requestItems();
            }

            final Event event = getItem(position);
            View view = convertView;
            ViewHolder childViews;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.event_summary, parent, false);
                childViews = new ViewHolder();
                view.setTag(childViews);
                childViews.date = (TextView) view.findViewById(R.id.date);
                childViews.title = (TextView) view.findViewById(R.id.title);
            } else {
                childViews = (ViewHolder) view.getTag();
            }
            childViews.date.setText(getDateFormatter().format(event.getStartTime()));
            childViews.title.setText(event.getTitle());

            return view;
        }

        private class ViewHolder {
            TextView date;
            TextView title;
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        if (!(getActivity() instanceof EventListListener)) {
            throw new IllegalStateException("Activity must implement fragment's listener");
        }
        ((EventListListener) getActivity()).onEventSelected(adapter.getItem(position).getId());
    }

}
