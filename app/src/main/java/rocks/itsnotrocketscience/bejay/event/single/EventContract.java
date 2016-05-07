package rocks.itsnotrocketscience.bejay.event.single;

import android.content.Context;

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
        void showToast(String toast);
        void onSongAdded(Song event);
        void notifyItemChanged(int position);
    }

    interface EventPresenter {
        void onViewAttached(EventView view);
        void onViewDetached();
        void registerUpdateReceiver(Context context);
        void unregisterUpdateReceiver();
        void onDestroy();
        void loadEvent(int id);
        void addSong(Song song);
        void toggleLike(Song song, int pos);
    }
}