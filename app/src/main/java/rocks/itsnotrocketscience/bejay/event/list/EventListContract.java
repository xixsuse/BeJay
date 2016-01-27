package rocks.itsnotrocketscience.bejay.event.list;

import java.util.List;

import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by nemi on 27/01/2016.
 */
public interface EventListContract {
    interface EventListView {
        void setProgressVisible(boolean visible);
        void onEventsLoaded(List<Event> events);
        void showError();
    }

    interface EventListPresenter {
        void onViewAttached(EventListView view);
        void onViewDetached();
        void onDestroy();
        void loadEvents();
    }
}
