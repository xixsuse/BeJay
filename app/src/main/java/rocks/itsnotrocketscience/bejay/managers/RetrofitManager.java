package rocks.itsnotrocketscience.bejay.managers;

import android.content.Context;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.CheckInUserToEvent;
import rocks.itsnotrocketscience.bejay.api.retrofit.GetEvents;
import rocks.itsnotrocketscience.bejay.api.retrofit.PostSong;
import rocks.itsnotrocketscience.bejay.base.AppApplication;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;

/**
 * Created by centralstation on 22/10/15.
 */
public class RetrofitManager extends RetrofitListeners {

    RestAdapter restAdapter;
    AppApplication application;

    public RetrofitManager(Context context, AccountManager accountManager) {

        application = (AppApplication) context.getApplicationContext();
        restAdapter = new RestAdapter.Builder()
                .setRequestInterceptor(accountManager.getAuthTokenInterceptor())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.API)
                .build();
    }

    public void checkInUser(CheckInListener listener, final int id) {

        CheckInUserToEvent checkIn = restAdapter.create(CheckInUserToEvent.class);
        checkIn.checkIn(id, new Callback<Event>() {
            @Override
            public void success(Event event, Response response) {
                listener.onCheckedIn(id, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onCheckedIn(id, error);
            }
        });
    }

    public void checkoutUser(CheckoutListener listener, int id) {

        CheckInUserToEvent checkIn = restAdapter.create(CheckInUserToEvent.class);
        checkIn.checkOut(id, new Callback<Event>() {
            @Override
            public void success(Event event, Response response) {
                listener.onCheckedOut(id, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onCheckedOut(id, error);
            }
        });
    }

    public void addSong(Song song, SongAddedListener songAddedListener) {

        PostSong postSong = restAdapter.create(PostSong.class);
        postSong.postSong(song, new Callback<Song>() {
            @Override
            public void success(Song song, Response response) {
                songAddedListener.onSongAdded(song, null);
            }

            @Override
            public void failure(RetrofitError error) {
                songAddedListener.onSongAdded(null, error);
            }
        });
    }
}
