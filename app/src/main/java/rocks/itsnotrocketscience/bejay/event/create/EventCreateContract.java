package rocks.itsnotrocketscience.bejay.event.create;


import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by sirfunkenstine on 29/03/16.
 *
 */
public class EventCreateContract {

    interface EventCreateView {
        void setProgressVisible(boolean visible);
        void showError(String error, int resource);
        void showToastError(String error);
        void onInitialDateSet(String date);
        void onInitialTimeSet(String time);
        void finish();
    }

    public interface EventCreatePresenter {
        void onViewAttached(EventCreateView view);
        void onViewDetached();
        void onDestroy();
        void postEvent(Event event);
        void fetchInitialDate();
        void fetchInitialTime();
        void onErrorFound(String message, int resource);
        void processEvent(Event eventObjectFromViews);
    }
}
