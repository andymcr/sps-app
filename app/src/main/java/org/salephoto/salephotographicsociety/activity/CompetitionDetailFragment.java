package org.salephoto.salephotographicsociety.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.salephoto.models.Competition;
import org.salephoto.salephotographicsociety.R;

import java.util.ArrayList;
import java.util.List;


public class CompetitionDetailFragment extends AbstractDetailFragment {
   private CompetitionAdapter adapter;


    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CompetitionAdapter();
        setAdapter(adapter);
    }

    private class CompetitionAdapter extends RecyclerView.Adapter<CompetitionAdapter.ViewHolder> {
        private List<Competition> competitions = new ArrayList<>();

        @Override
        public CompetitionAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                final int viewType) {
            final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.competition_future, parent, false);
            final ViewHolder childViews = new ViewHolder(view);
            childViews.date = (TextView) view.findViewById(R.id.date);
            childViews.title = (TextView) view.findViewById(R.id.title);
            childViews.judges = (TextView) view.findViewById(R.id.judges);
            return childViews;
        }

        @Override
        public void onBindViewHolder(final ViewHolder childViews, final int position) {
            final Competition competition = competitions.get(position);
            childViews.date.setText("Date");
            childViews.title.setText(competition.getTitle());
            childViews.judges.setText("Judge");
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView date;
            public TextView title;
            public TextView judges;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
