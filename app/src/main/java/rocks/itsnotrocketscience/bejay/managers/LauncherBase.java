package rocks.itsnotrocketscience.bejay.managers;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import rocks.itsnotrocketscience.bejay.event.list.EventListType;

public class LauncherBase implements Launcher {

    @Override public void openHome() {
        throw new UnsupportedOperationException();
    }

    @Override public void openProfile() {
        throw new UnsupportedOperationException();
    }

    @Override public void openEvent(int eventId) {
        throw new UnsupportedOperationException();
    }

    @Override public void logout() {
        throw new UnsupportedOperationException();
    }

    @Override public void finishMapActivityForResult(LatLng latLng, String place) {
        throw new UnsupportedOperationException();
    }

    @Override public void openEventList(EventListType trye) {
        throw new UnsupportedOperationException();
    }

    @Override public void openCreateEvent() {
        throw new UnsupportedOperationException();
    }

    @Override public void openMapActivityForResult(Fragment fragment) {
        throw new UnsupportedOperationException();
    }
}
