package org.salephoto.salephotographicsociety.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.salephoto.salephotographicsociety.view.NavigationDrawerLayout;
import org.salephoto.salephotographicsociety.R;
import org.salephoto.salephotographicsociety.bus.BusProvider;
import org.salephoto.salephotographicsociety.events.EventEvent;
import org.salephoto.salephotographicsociety.repository.ApiErrorEvent;
import org.salephoto.salephotographicsociety.repository.RepositoryProvider;
import org.salephoto.salephotographicsociety.repository.RepositoryService;

import retrofit.RetrofitError;


public class MainActivity extends ActionBarActivity
        implements EventListFragment.EventListListener,
            CompetitionListFragment.CompetitionListListener {
    private static final String ACTIVATED_POSITION = "activatedPosition";

    private NavigationDrawerLayout mainLayout;
    private boolean dualPane;
    private CompetitionDetailFragment competitionDetails;

    private Bus bus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dualPane = findViewById(R.id.details) != null;

        mainLayout = (NavigationDrawerLayout) findViewById(R.id.drawer);
        mainLayout.setupDrawerConfiguration((ListView) findViewById(R.id.navigation_menu),
            R.layout.navigation_item, R.id.actionbar_toolbar);

        mainLayout.addNavigationItem(R.string.navigation_menu_label_events,
            R.string.navigation_menu_label_events, EventListPagerFragment.class);
        mainLayout.addNavigationItem(R.string.navigation_menu_label_competitions,
            R.string.navigation_menu_label_competitions, CompetitionListPagerFragment.class);
        mainLayout.addNavigationItem(R.string.navigation_menu_label_talks,
            R.string.navigation_menu_label_talks, TalkListPagerFragment.class);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(ACTIVATED_POSITION)){
            mainLayout.onNavigationItemSelected(savedInstanceState.getInt(ACTIVATED_POSITION));
        } else {
            mainLayout.onNavigationItemSelected(1, false);
        }

        competitionDetails = new CompetitionDetailFragment();

        final RepositoryService repositoryService = RepositoryProvider.getRepository();
        getBus().register(repositoryService);
        getBus().register(this);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (mainLayout.getActivatedPosition() != ListView.INVALID_POSITION) {
            savedInstanceState.putInt(ACTIVATED_POSITION, mainLayout.getActivatedPosition());
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mainLayout.isDrawerOpen();
// TODO
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mainLayout.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mainLayout.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mainLayout.getDrawerToggle().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Intent intent = new Intent(this, ApiServiceService.class);
        // TODO DeadObjectException
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (bound) {
//            unbindService(serviceConnection);
//            bound = false;
//        }
    }

//    private ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName className, IBinder binder) {
//            ApiServiceService.ApiBinder apiBinder = (ApiServiceService.ApiBinder) binder;
//            service = apiBinder.getService();
//            bound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            bound = false;
//        }
//    };

    @Override
    public void onResume() {
        super.onResume();

        getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        getBus().unregister(this);
    }

    private Bus getBus() {
        if (bus == null) {
            bus = BusProvider.getBus();
        }

        return bus;
    }

    @Override
    public void onEventSelected(int eventId) {
//        getBus().post(new GetEventEvent(eventId));
    }

    @Subscribe
    public void onEventLoaded(final EventEvent event) {
//        final EventFragment fragment = EventFragment.newInstance(event.getEvent());
//        if (dualPane) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.details, fragment)
//                            // just using commit causes an IllegalStateException when device resumes
//                    .commitAllowingStateLoss();
//        } else {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.main_contents, fragment)
//                    .addToBackStack(null)
//                    .commit();
//        }
    }

    @Override
    public void onCompetitionSelected(int competitionId) {
        competitionDetails.setCurrentItemId(competitionId);
        useFragment(competitionDetails);
    }

    private void useFragment(final Fragment fragment) {
        if (dualPane) {
            setDetailsFragment(fragment);
        } else {
            setMainFragment(fragment);
        }
    }

    public void setMainFragment(final Fragment fragment) {
        setMainFragment(fragment, true);
    }

    public void setMainFragment(final Fragment fragment, final boolean saveState) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_contents, fragment);
        if (saveState) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void setDetailsFragment(final Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.details, fragment)
            .commit();
    }

    @Override
    public void onBackPressed() {
        if (mainLayout.isDrawerOpen()) {
            mainLayout.closeDrawer();
        } else {
            super.onBackPressed();
            mainLayout.onBackPressed(getSupportFragmentManager().findFragmentById(R.id.details));
        }
    }

    @Subscribe
    public void onApiError(ApiErrorEvent event) {
//        toast("Something went wrong, please try again.");
        final RetrofitError error = event.getError();
        if (error.getKind() == RetrofitError.Kind.NETWORK) {
            Log.e(getResources().getString(R.string.app_name), "API (network) error", event.getError());
        } else if (error.getKind() == RetrofitError.Kind.CONVERSION) {
            Log.e(getResources().getString(R.string.app_name), "API (conversion) error", event.getError());
        } else if (error.getKind() == RetrofitError.Kind.HTTP) {
            Log.e(getResources().getString(R.string.app_name),
                event.getError().getMessage() + " (" + event.getError().getUrl() +  ")");
        } else {
            Log.e(getResources().getString(R.string.app_name), "API (unexpected) error", event.getError());
        }
    }

}
