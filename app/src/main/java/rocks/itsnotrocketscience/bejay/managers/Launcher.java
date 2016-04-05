package rocks.itsnotrocketscience.bejay.managers;

import android.support.v4.app.Fragment;

public interface Launcher {
    void openHome();
    void openProfile();
    void openEvent(int eventId);
    void logout();
    void openLogin();
    void openEventList();
    void openCreateEvent();
    void openMapActivityForResult(Fragment fragment);
}
