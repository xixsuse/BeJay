package rocks.itsnotrocketscience.bejay.managers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.event.create.EventCreateActivity;
import rocks.itsnotrocketscience.bejay.event.create.MapActivity;
import rocks.itsnotrocketscience.bejay.event.list.EventListFragment;
import rocks.itsnotrocketscience.bejay.event.single.EventActivity;
import rocks.itsnotrocketscience.bejay.home.HomeFragment;
import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.login.LoginFragment;
import rocks.itsnotrocketscience.bejay.profile.ProfileFragment;

public class AppLauncher implements Launcher {

    private final AppCompatActivity activity;
    private final SharedPreferences sharedPreferences;

    public AppLauncher(AppCompatActivity activity, SharedPreferences sharedPreferences) {
        this.activity = activity;
        this.sharedPreferences = sharedPreferences;
    }

    public void showFragment(Fragment fragment) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void openHome() {
        showFragment(HomeFragment.newInstance());
    }

    @Override
    public void openProfile() {
        showFragment(ProfileFragment.newInstance());
    }

    @Override
    public void openEvent(int eventId) {
        Intent intent = new Intent(activity, EventActivity.class);
        intent.putExtra(EventActivity.EVENT_ID, eventId);
        activity.startActivity(intent);
    }

    @Override
    public void logout() {
        sharedPreferences.edit().putBoolean(Constants.IS_LOGGED_IN, false).apply();
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void openLogin() {
        showFragment(LoginFragment.newInstance());
    }

    @Override
    public void openEventList() {
        showFragment(EventListFragment.newInstance());
    }

    @Override
    public void openCreateEvent() {
        Intent intent = new Intent(activity, EventCreateActivity.class);
        activity.startActivity(intent);
    }

    @Override public void openMapActivityForResult() {
        Intent intent = new Intent(activity, MapActivity.class);
        activity.startActivityForResult(intent,1);
    }


}
