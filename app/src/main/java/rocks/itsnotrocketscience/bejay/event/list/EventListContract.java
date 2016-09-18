package rocks.itsnotrocketscience.bejay.event.list;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by nemi on 27/01/2016.
 */
public interface EventListContract {
    interface EventListView {
        void setProgressVisible(boolean visible);
        void onEventsLoaded(List<Event> events);
        void showError(String text);
        void onCheckInFailed(Event event, @EventListPresenter.CheckInError int reason);
    }

    interface EventListPresenter {
        int CHECK_IN_CHECKOUT_NEEDED = -1;
        int CHECK_IN_FAILED = -2;

        @Retention(RetentionPolicy.SOURCE)
        @IntDef(value = {CHECK_IN_CHECKOUT_NEEDED, CHECK_IN_FAILED})
        @interface CheckInError {

        }

        void onViewAttached(EventListView view);
        void setListType(EventListType listType);
        EventListType getType();
        void onViewDetached();
        void onDestroy();
        void loadEvents();
        void openEvent();
        void openCreateEvent();
        void checkIn(Event event, boolean force);
        void searchEvent(String query);
    }
}
