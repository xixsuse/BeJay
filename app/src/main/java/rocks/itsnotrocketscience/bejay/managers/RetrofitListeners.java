package rocks.itsnotrocketscience.bejay.managers;

import retrofit.RetrofitError;
import rocks.itsnotrocketscience.bejay.models.Song;

/**
 * Created by lduf0001 on 21/12/15.
 */
public class RetrofitListeners {

    public interface CheckoutListener {
        void onCheckedOut(int id, RetrofitError error);
    }

    public interface SongAddedListener {
        void onSongAdded(Song song, RetrofitError error);
    }
}
