package rocks.itsnotrocketscience.bejay.managers;

import java.util.ArrayList;

import retrofit.RetrofitError;
import rocks.itsnotrocketscience.bejay.models.CmsUser;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;
import rocks.itsnotrocketscience.bejay.models.Token;

/**
 * Created by lduf0001 on 21/12/15.
 */
public class RetrofitListeners {

    public interface EventListListener {
        void onEventFeedLoaded(ArrayList<Event> list, RetrofitError error);
    }

    public interface CheckoutListener {
        void onCheckedOut(int id, RetrofitError error);
    }

    public interface CheckInListener {
        void onCheckedIn(int id, RetrofitError error);
    }

    public interface EventListener {
        void onEventLoaded(Event list, RetrofitError error);
    }

    public interface LoginListener {
        void onLoggedIn(Token token, RetrofitError error);
    }

    public interface RegisterListener {
        void onRegistered(CmsUser user, RetrofitError error);
    }

    public interface SongAddedListener {
        void onSongAdded(Song song, RetrofitError error);
    }
}
