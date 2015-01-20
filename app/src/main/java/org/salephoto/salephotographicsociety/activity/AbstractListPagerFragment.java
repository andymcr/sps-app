package org.salephoto.salephotographicsociety.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.salephoto.salephotographicsociety.R;


public class AbstractListPagerFragment extends Fragment {
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.summary_list, container, false);
    }

    protected abstract class ListPagerAdapter extends FragmentPagerAdapter {
        public ListPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            if (position == 0) {
                return getResources().getString(R.string.tab_label_past);
            } else {
                return getResources().getString(R.string.tab_label_future);
            }
        }
    }
}
