package rocks.itsnotrocketscience.bejay.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.login.LoginOrRegisterFragment;
import rocks.itsnotrocketscience.bejay.main.NavigationDrawerFragment;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;

/**
 * Created by centralstation on 11/09/15.
 */
public class BaseActivity extends AppCompatActivity {

    public Toolbar toolbar;
    protected NavigationDrawerFragment mNavigationDrawerFragment;
    @Inject SharedPreferences sharedPreferences;
    @Inject AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppApplication().getNetComponent().inject(this);
        if (!accountManager.isLoggedIn()) {
            logout();
            return;
        }
        setContentView(R.layout.activity_main);
        if (toolbar != null) {
            toolbar.setTitle("Navigation Drawer");
            setSupportActionBar(toolbar);
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    private void logout() {
        sharedPreferences.edit().putBoolean(LoginOrRegisterFragment.IS_LOGGED_IN, false).apply();
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        startActivity(intent);

        this.finish();
    }

    public void showFragment(Fragment fragment) {
        android.app.FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    protected AppApplication getAppApplication(){

        return (AppApplication)getApplication();
    }

}
