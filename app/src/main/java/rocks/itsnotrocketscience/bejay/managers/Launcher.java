package rocks.itsnotrocketscience.bejay.managers;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import rocks.itsnotrocketscience.bejay.event.list.EventListType;

public interface Launcher {
    void search();
    void openProfile();
    void openEventActivity(int eventId);
    void openEventFragment();
    void logout();
    void openEventList(EventListType type);
    void openCreateEvent();
    void openMapActivityForResult(Fragment fragment);
    void finishMapActivityForResult(LatLng latLng, String location);
}
