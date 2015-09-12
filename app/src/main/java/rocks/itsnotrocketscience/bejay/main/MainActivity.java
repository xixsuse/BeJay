package rocks.itsnotrocketscience.bejay.main;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;
import rocks.itsnotrocketscience.bejay.home.HomeFragment;
import rocks.itsnotrocketscience.bejay.main.nav_items.FragmentNavItem;
import rocks.itsnotrocketscience.bejay.main.nav_items.LoginNavItem;
import rocks.itsnotrocketscience.bejay.main.nav_items.NavItem;
import rocks.itsnotrocketscience.bejay.profile.ProfileFragment;

public class MainActivity extends BaseActivity {

    private static List<NavItem> items;
    public Toolbar toolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (toolbar != null) {
            toolbar.setTitle("Navigation Drawer");
            setSupportActionBar(toolbar);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setItems();
        mNavigationDrawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), items);
        mTitle = getTitle();

    }

    private void setItems() {
        items = new ArrayList<>();
        items.add(new FragmentNavItem(this, "Home", HomeFragment.newInstance()));
        items.add(new FragmentNavItem(this, "Profile", ProfileFragment.newInstance()));
        items.add(new LoginNavItem(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}