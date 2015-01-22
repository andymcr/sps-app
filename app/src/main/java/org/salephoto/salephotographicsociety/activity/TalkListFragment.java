package org.salephoto.salephotographicsociety.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.salephoto.models.Talk;
import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.events.ListTalksEvent;
import org.salephoto.salephotographicsociety.events.TalksListedEvent;


public class TalkListFragment extends AbstractEventListFragment {
    private TalkListAdapter adapter;

    public interface TalkListListener {
        void onTalkSelected(int talkId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new TalkListAdapter(getActivity());
        setListAdapter(adapter);
    }

    public void requestItems() {
        getBus().post(new ListTalksEvent(getArguments().getInt(ARG_ID),
            getBefore(), getAfter(), 5, getListAdapter().getCount(), getOrder()));
    }

    @Subscribe
    public void onTalksLoaded(TalksListedEvent event) {
        if (event.getRequesterId() == getArguments().getInt(ARG_ID)) {
            adapter.addAll(event.getList());
        }
    }

    private class TalkListAdapter extends AbstractListFragment.ListAdapter<Talk> {
        public TalkListAdapter(final Context context) {
            super(context);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            if (!isListComplete() && ((position + 1) == this.getCount())) {
                requestItems();
            }

            final Talk talk = getItem(position);
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
            childViews.date.setText(getDateFormatter().format(talk.getStartTime()));
            childViews.title.setText(talk.getTitle());

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

        if (!(getActivity() instanceof TalkListListener)) {
            throw new IllegalStateException("Activity must implement fragment's listener");
        }
        ((TalkListListener) getActivity()).onTalkSelected(adapter.getItem(position).getId());
    }

}
