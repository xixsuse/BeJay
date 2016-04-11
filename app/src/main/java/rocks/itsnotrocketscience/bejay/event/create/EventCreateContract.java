package rocks.itsnotrocketscience.bejay.event.create;


import android.location.Location;

import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by sirfunkenstine on 29/03/16.
 */
public class EventCreateContract {

    interface EventCreateView {
        void setProgressVisible(boolean visible);
        void showError(String error);
    }

    public interface EventCreatePresenter {
        void onViewAttached(EventCreateView view);
        void onViewDetached();
        void onDestroy();
        void postEvent(Event event);
    }
}
