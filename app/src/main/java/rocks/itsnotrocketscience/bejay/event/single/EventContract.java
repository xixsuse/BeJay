package rocks.itsnotrocketscience.bejay.event.single;

import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;

/**
 * Created by nemi on 27/01/2016.
 */
public interface EventContract {

    interface EventView {
        void setProgressVisible(boolean visible);
        void onEventLoaded(Event event);
        void showError(String error);
        void onSongAdded(Song event);
    }

    interface EventPresenter {
        void onViewAttached(EventView view);
        void onViewDetached();
        void onDestroy();
        void loadEvent(int id);
        void adSong(Song song);
    }
}