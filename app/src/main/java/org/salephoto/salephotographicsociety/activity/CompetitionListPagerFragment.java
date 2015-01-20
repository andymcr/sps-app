package org.salephoto.salephotographicsociety.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.events.AbstractListEvent;
import org.salephoto.salephotographicsociety.events.ListEventEvent;

import java.util.Date;


public class CompetitionListPagerFragment extends AbstractListPagerFragment {
    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);

        final CompetitionListPagerAdapter pagerAdapter = new CompetitionListPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
    }

    private class CompetitionListPagerAdapter extends ListPagerAdapter {
        public CompetitionListPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new CompetitionListFragment();
            Bundle args = new Bundle();
            args.putInt(AbstractListFragment.ARG_ID, position);
            args.putString(AbstractListFragment.ARG_ORDER_FIELD, ListEventEvent.FIELD_DATE);
            if (position == 0) {
                args.putString(AbstractListFragment.ARG_ORDER_DIRECTION,
                    AbstractListEvent.Order.Descending.name());
                args.putSerializable(AbstractEventListFragment.ARG_BEFORE_DATE, new Date());
            } else {
                args.putString(AbstractListFragment.ARG_ORDER_DIRECTION,
                    AbstractListEvent.Order.Ascending.name());
                args.putSerializable(AbstractEventListFragment.ARG_AFTER_DATE, new Date());
            }
            fragment.setArguments(args);
            return fragment;

        }
    }
}
