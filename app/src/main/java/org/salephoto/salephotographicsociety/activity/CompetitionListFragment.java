package org.salephoto.salephotographicsociety.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.salephoto.models.Competition;
import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.events.CompetitionsListedEvent;
import org.salephoto.salephotographicsociety.events.ListCompetitionsEvent;


public class CompetitionListFragment extends AbstractEventListFragment {
    private CompetitionListAdapter adapter;

    public interface CompetitionListListener {
        void onCompetitionSelected(int competitionId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new CompetitionListAdapter(getActivity());
        setListAdapter(adapter);
    }

    public void requestItems() {
        getBus().post(new ListCompetitionsEvent(getArguments().getInt(ARG_ID),
            getBefore(), getAfter(), 5, getListAdapter().getCount(), getOrder()));
    }

    @Subscribe
    public void onCompetitionsLoaded(CompetitionsListedEvent event) {
        if (event.getRequesterId() == getArguments().getInt(ARG_ID)) {
            adapter.addAll(event.getList());
        }
    }

    private class CompetitionListAdapter extends ListAdapter<Competition> {
        public CompetitionListAdapter(final Context context) {
            super(context);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            if (!isListComplete() && ((position + 1) == getCount())) {
                requestItems();
            }

            final Competition competition = getItem(position);
            View view = convertView;
            ViewHolder childViews;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.competition_summary, parent, false);
                childViews = new ViewHolder();
                view.setTag(childViews);
                childViews.handinDate = (TextView) view.findViewById(R.id.handin_date);
                childViews.date = (TextView) view.findViewById(R.id.date);
                childViews.title = (TextView) view.findViewById(R.id.title);
            } else {
                childViews = (ViewHolder) view.getTag();
            }
            childViews.handinDate.setText(getDateFormatter().format(competition.getHandinDate()));
            childViews.date.setText(getDateFormatter().format(competition.getStartTime()));
            childViews.title.setText(competition.getTitle());

            return view;
        }

        private class ViewHolder {
            public TextView handinDate;
            public TextView date;
            public TextView title;
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        if (!(getActivity() instanceof CompetitionListListener)) {
            throw new IllegalStateException("Activity must implement fragment's listener");
        }
        ((CompetitionListListener) getActivity()).onCompetitionSelected(adapter.getItem(position).getId());
    }

}
