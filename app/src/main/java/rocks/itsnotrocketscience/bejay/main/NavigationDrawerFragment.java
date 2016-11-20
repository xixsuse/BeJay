package rocks.itsnotrocketscience.bejay.main;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

public class NavigationDrawerFragment extends BaseFragment<ActivityComponent> {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    @Inject public SharedPreferences sharedPreferences;
    @Inject public AccountManager accountManager;
    @Inject public Launcher launcher;
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private int currentSelectedPosition = 0;
    private boolean fromSavedInstanceState;
    private boolean userLearnedDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        userLearnedDrawer = sharedPreferences.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (null != savedInstanceState) {
            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            fromSavedInstanceState = true;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        navigationView = (NavigationView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        launcher.search();

        navigationView.setNavigationItemSelectedListener(this::chooseAction);
        if (accountManager.isCheckedIn()) {
            navigationView.getMenu().findItem(R.id.event).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.event).setVisible(false);
        }
        setupHeader();
        return navigationView;
    }

    private boolean chooseAction(MenuItem menuItem) {
        if (menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        ((BaseActivity)getActivity()).setHeaderTitle(menuItem.getTitle());

        switch (menuItem.getItemId()) {

            case R.id.search:
                launcher.search();
                break;
            case R.id.profile:
                launcher.openProfile();
                return true;
            case R.id.event:
                launcher.openEventActivity(accountManager.getCheckedInEventId());
                return true;
            case R.id.settings:
                return true;
            case R.id.logout:
                launcher.logout();
                return true;
        }
        return false;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        View fragmentContainerView = getActivity().findViewById(fragmentId);
        this.drawerLayout = drawerLayout;

        this.drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        Toolbar toolbar = ((BaseActivity) getActivity()).toolbar;

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                NavigationDrawerFragment.this.drawerLayout,                    /* DrawerLayout object */
                toolbar,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!userLearnedDrawer) {
                    userLearnedDrawer = true;
                    sharedPreferences.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

            }
        };

        if (!userLearnedDrawer && !fromSavedInstanceState) {
            this.drawerLayout.openDrawer(fragmentContainerView);
        }

        this.drawerLayout.post(mDrawerToggle::syncState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, currentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    private void setupHeader() {
        View view = navigationView.inflateHeaderView(R.layout.header);

        TextView name = (TextView) view.findViewById(R.id.tvUsername);
        name.setText(sharedPreferences.getString(Constants.USERNAME, ""));

        TextView email = (TextView) view.findViewById(R.id.tvEmail);
        email.setText(sharedPreferences.getString(Constants.EMAIL, ""));

    }
}
