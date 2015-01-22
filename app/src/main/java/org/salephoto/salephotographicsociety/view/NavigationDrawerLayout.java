package org.salephoto.salephotographicsociety.view;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.activity.MainActivity;


public class NavigationDrawerLayout extends DrawerLayout {
    private static final String ARG_TITLE = "title";

    private Toolbar toolbar;
    private ListView navigationView;
    private ArrayAdapter<NavigationItem> adapter;
    private int navigationItemLayoutId;
    private ActionBarDrawerToggle drawerToggle;
    private int activatedPosition = ListView.INVALID_POSITION;

    public NavigationDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public NavigationDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationDrawerLayout(Context context) {
        super(context);
    }

    public void setupDrawerConfiguration(final ListView navigationView, final int navigationItemLayoutId,
            final int toolbarId) {
        toolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(toolbar);

        this.navigationView = navigationView;
        this.navigationItemLayoutId = navigationItemLayoutId;

        // order matters for JellyBean
        navigationView.addHeaderView(View.inflate(getActivity(), R.layout.navigation_header, null));
        adapter = new NavigationAdapter();
        navigationView.setAdapter(adapter);

        navigationView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onNavigationItemSelected(position);
                closeDrawer(navigationView);
            }
        });

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = setupDrawerToggle();
        setDrawerListener(drawerToggle);
        // set a custom shadow that overlays the main content when the drawer
//        setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // Setup action buttons
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void addNavigationItem(final int menuLabelId, final int windowTitleId,
            final Class<? extends Fragment> fragmentClass) {
        adapter.add(new NavigationItem(getResources().getString(menuLabelId),
            getResources().getString(windowTitleId), fragmentClass));
    }

    public void onNavigationItemSelected(final int position) {
        onNavigationItemSelected(position, true);
    }

    public void onNavigationItemSelected(final int position, final boolean saveState) {
        try {
            final NavigationItem item = adapter.getItem(position - 1 /* header */);
            final Fragment fragment = item.newInstance();
            setMainFragment(fragment, saveState);

            navigationView.setItemChecked(position, true);
            setTitle(fragment.getArguments().getString(ARG_TITLE));
            activatedPosition = position;
        } catch (IllegalAccessException | InstantiationException e) {
            Log.e(getClass().getSimpleName(), "x", e);
        }
    }

    public void onBackPressed(final Fragment newFragment) {
        if (newFragment != null) { /* Not leaving  application */
            setTitle(newFragment.getArguments().getString(ARG_TITLE));
        }
    }

    public int getActivatedPosition() {
        return activatedPosition;
    }

    private class NavigationAdapter extends ArrayAdapter<NavigationItem> {
        NavigationAdapter() {
            super(getActivity(), navigationItemLayoutId, new ArrayList<NavigationItem>());
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(navigationItemLayoutId, parent, false);
            }
            final TextView text = (TextView) view.findViewById(R.id.text_item);
            text.setText(getItem(position).getLabel());

            return view;
        }
    }

    public void closeDrawer() {
        closeDrawer(navigationView);
    }

    private FragmentActivity getActivity() {
        return (FragmentActivity) getContext();
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }

    private ActionBar getSupportActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    private void setMainFragment(final Fragment fragment, final boolean saveState) {
        ((MainActivity) getActivity()).setMainFragment(fragment, saveState);
    }

    public boolean isDrawerOpen() {
        return isDrawerOpen(navigationView);
    }

    private void setSupportActionBar(final Toolbar toolbar) {
        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    private class NavigationItem {
        private String label;
        private Class<? extends Fragment> fragmentClass;
        private Bundle fragmentArgs;

        public NavigationItem(final String label, final String title,
                final Class<? extends Fragment> fragmentClass) {
            this(label, title, fragmentClass, null);
        }

        public NavigationItem(final String label, final String title,
                Class<? extends Fragment> fragmentClass, final Bundle args) {
            this.label = label;
            this.fragmentClass = fragmentClass;
            this.fragmentArgs = args;
            if (this.fragmentArgs == null) {
                this.fragmentArgs = new Bundle();
            }
            fragmentArgs.putString(ARG_TITLE, title);
        }

        public String getLabel() {
            return label;
        }

        public Fragment newInstance()
                throws IllegalAccessException, InstantiationException {
            Fragment fragment = fragmentClass.newInstance();
            fragment.setArguments(fragmentArgs);

            return fragment;
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(getActivity(), this, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                // setTitle(getCurrentTitle());
                // call onPrepareOptionsMenu()
                getActivity().supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                // setTitle("Navigate");
                // call onPrepareOptionsMenu()
                getActivity().supportInvalidateOptionsMenu();
            }
        };
    }

}