package rocks.itsnotrocketscience.bejay.managers;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

public interface Launcher {
    void openHome();
    void openProfile();
    void openEvent(int eventId);
    void logout();
    void openEventList();
    void openCreateEvent();
    void openMapActivityForResult(Fragment fragment);
    void finishMapActivityForResult( LatLng latLng);
}
