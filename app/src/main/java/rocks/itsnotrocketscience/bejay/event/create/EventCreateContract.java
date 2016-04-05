package rocks.itsnotrocketscience.bejay.event.create;


import android.location.Location;

/**
 * Created by sirfunkenstine on 29/03/16.
 */
public class EventCreateContract {

    interface EventCreateView {
        void setProgressVisible(boolean visible);
        void showError(String error);
        void onGapsAcquire(Location locatin);
    }

    public interface EventCreatePresenter {
        void onViewAttached(EventCreateView view);
        void onViewDetached();
        void onDestroy();
        void getCurrentGPS();
    }
}
