package rocks.itsnotrocketscience.bejay.managers;

import rocks.itsnotrocketscience.bejay.R;

/**
 * Created by centralstation on 27/10/15.
 */
class LaunchManager {

    public LaunchManager() {
    }

    public static void showFragment(android.support.v4.app.Fragment fragment, android.support.v4.app.FragmentManager manager) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
