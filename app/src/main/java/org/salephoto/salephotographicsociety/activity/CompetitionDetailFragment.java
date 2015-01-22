package org.salephoto.salephotographicsociety.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.salephoto.models.Competition;
import org.salephoto.models.Person;
import org.salephoto.models.Section;
import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.events.AbstractListEvent;
import org.salephoto.salephotographicsociety.events.AbstractListEventEvent;
import org.salephoto.salephotographicsociety.events.CompetitionEvent;
import org.salephoto.salephotographicsociety.events.CompetitionsListedEvent;
import org.salephoto.salephotographicsociety.events.GetCompetitionEvent;
import org.salephoto.salephotographicsociety.events.ListCompetitionsEvent;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CompetitionDetailFragment extends AbstractDetailFragment {
    private static Set<String> fieldsNeeded;

    private CompetitionAdapter adapter;

    public static CompetitionDetailFragment newInstance(final int initialItemId) {
        final CompetitionDetailFragment fragment = new CompetitionDetailFragment();
        fragment.setInitialItemId(initialItemId);

        return fragment;
    }

    public CompetitionDetailFragment() {
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

        getBus().post(new GetCompetitionEvent(initialItemId));
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CompetitionAdapter();
        setAdapter(adapter);
    }

    @Subscribe
    public void onListSeeded(final CompetitionEvent event) {
        adapter.add(0, event.getCompetition());
    }

    @Subscribe
    public void onExtendList(final CompetitionsListedEvent event) {
        if (event.getList().size() > 0) {
            final Competition newCompetition = event.getList().get(0);
            final Competition firstCompetition = adapter.first();
            if ((firstCompetition == null)
                    || newCompetition.getStartTime().before(firstCompetition.getStartTime())) {
                adapter.add(0, newCompetition);
            } else {
                adapter.add(adapter.getItemCount(), newCompetition);
            }
        }
    }

    private class CompetitionAdapter extends DetailAdapter<Competition, CompetitionAdapter.ViewHolder> {
        @Override
        public CompetitionAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                final int viewType) {
            final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.competition_detail, parent, false);
            final ViewHolder childViews = new ViewHolder(view);
            childViews.title = (TextView) view.findViewById(R.id.title);
            childViews.date = (TextView) view.findViewById(R.id.date);
            childViews.secretaryLabel = (TextView) view.findViewById(R.id.secretary_label);
            childViews.secretary = (TextView) view.findViewById(R.id.secretary);
            childViews.judges = (TextView) view.findViewById(R.id.judges);
            childViews.sections = (ListView) view.findViewById(R.id.sections);
            childViews.sectionsAdapter = new SectionAdapter();
            childViews.sections.setAdapter((childViews.sectionsAdapter));
            TextView emptyList = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.empty_list, null, false);
            childViews.sections.setEmptyView(emptyList);
            childViews.sections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    childViews.sectionsAdapter.setExpandedPosition(position);
                }
            });
            emptyList.setText("No sections");

            return childViews;
        }

        @Override
        public void onBindViewHolder(final ViewHolder childViews, final int position) {
            final Competition competition = get(position);
            childViews.title.setText(competition.getTitle());
            if (competition.getHandinDate().after(new Date())) {
                childViews.date.setText(formatDate(competition.getHandinDate())
                    + "/" + formatDate(competition.getStartTime()));
                if (competition.getSecretary() != null) {
                    childViews.secretary.setText(competition.getSecretary().getName());
                } else {
                    childViews.secretary.setText("");
                }
                childViews.secretaryLabel.setVisibility(View.VISIBLE);
                childViews.secretary.setVisibility(View.VISIBLE);
            } else {
                childViews.date.setText(formatDate(competition.getStartTime()));
                childViews.secretaryLabel.setVisibility(View.GONE);
                childViews.secretary.setVisibility(View.GONE);
            }
            final List<Person> judges = competition.getJudges();
            if ((judges != null) && (judges.size() > 0)) {
                childViews.judges.setText(competition.getJudges().get(0).getName());
            } else {
                childViews.judges.setText("");
            }
            childViews.sectionsAdapter.clear();
            if (competition.getSections()!= null){
                childViews.sectionsAdapter.addAll(competition.getSections());
            }
            childViews.sectionsAdapter.setExpandedPosition(ListView.INVALID_POSITION);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView date;
            public TextView title;
            public TextView secretaryLabel;
            public TextView secretary;
            public TextView judges;
            public ListView sections;
            public SectionAdapter sectionsAdapter;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public void requestPrevious(final Date date) {
            getBus().post(new ListCompetitionsEvent(ListView.INVALID_POSITION, date, null, 1, 0,
                new Pair<>(AbstractListEventEvent.FIELD_DATE, AbstractListEvent.Order.Descending),
                fieldsNeeded));
        }

        @Override
        public void requestNext(final Date date) {
            getBus().post(new ListCompetitionsEvent(ListView.INVALID_POSITION, null, date, 1, 0,
                new Pair<>(AbstractListEventEvent.FIELD_DATE, AbstractListEvent.Order.Ascending),
                fieldsNeeded));
        }
    }

    private class SectionAdapter extends ArrayAdapter<Section> {
        private static final int SUMMARY_VIEW = 0;
        private static final int RESULTS_VIEW = 1;

        private int expandedPosition = ListView.INVALID_POSITION;

        public SectionAdapter() {
            super(getActivity(), R.layout.section_summary);
        }

        public void setExpandedPosition(final int position) {
            expandedPosition = position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(final int position) {
            return position == expandedPosition ? RESULTS_VIEW : SUMMARY_VIEW;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            final int viewType = getItemViewType(position);
            View view = convertView;
            ViewHolder childViews;
            if(convertView == null) {
                childViews = new ViewHolder();
                if (viewType == SUMMARY_VIEW) {
                    view = LayoutInflater.from(getActivity())
                        .inflate(R.layout.section_summary, parent, false);
                    childViews.summary = (TextView) view.findViewById(R.id.section_summary);
                } else {
                    view = LayoutInflater.from(getActivity())
                        .inflate(R.layout.section_results, parent, false);
                }
                view.setTag(childViews);
            } else {
                childViews = (ViewHolder) view.getTag();
            }

            if (viewType == SUMMARY_VIEW) {
                childViews.summary.setText(getItem(position).getTitle());
            }

            return view;
        }

        private class ViewHolder {
            public TextView summary;
        }
    }
}
