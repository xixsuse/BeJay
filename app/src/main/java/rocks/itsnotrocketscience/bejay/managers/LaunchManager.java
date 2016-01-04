package rocks.itsnotrocketscience.bejay.managers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.event.single.EventActivity;

/**
 * Created by centralstation on 27/10/15.
 */
public class LaunchManager {

    public LaunchManager() {
    }

    public static void launchEvent(int id, Activity activity) {
        Intent intent = new Intent(activity, EventActivity.class);
        intent.putExtra(EventActivity.EVENT_ID, id);
        activity.startActivity(intent);
    }

    public static void showFragment(Fragment fragment, FragmentManager manager) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public static void showFragment(android.support.v4.app.Fragment fragment, android.support.v4.app.FragmentManager manager) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}
