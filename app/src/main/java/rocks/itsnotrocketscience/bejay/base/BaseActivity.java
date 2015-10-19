package rocks.itsnotrocketscience.bejay.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.home.HomeFragment;
import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.login.LoginOrRegisterFragment;
import rocks.itsnotrocketscience.bejay.main.NavigationDrawerFragment;
import rocks.itsnotrocketscience.bejay.main.nav_items.FragmentNavItem;
import rocks.itsnotrocketscience.bejay.main.nav_items.LoginNavItem;
import rocks.itsnotrocketscience.bejay.main.nav_items.NavItem;
import rocks.itsnotrocketscience.bejay.profile.ProfileFragment;

/**
 * Created by centralstation on 11/09/15.
 *
 */
public class BaseActivity extends AppCompatActivity {

    public Toolbar toolbar;
    protected NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!getAppApplication().getAccountManager().isLoggedIn()){
            logout();
        }
        setContentView(R.layout.activity_main);
        if (toolbar != null) {
            toolbar.setTitle("Navigation Drawer");
            setSupportActionBar(toolbar);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        mNavigationDrawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    private void logout() {
        getAppApplication().getSharedPreferences().edit().putBoolean(LoginOrRegisterFragment.IS_LOGGED_IN, false);
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    public AppApplication getAppApplication(){
        return (AppApplication)getApplication();
    }

    public void showFragment(Fragment fragment)
    {
        android.app.FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}
