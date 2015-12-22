package rocks.itsnotrocketscience.bejay.managers;

import android.content.Context;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.AuthCredentials;
import rocks.itsnotrocketscience.bejay.api.retrofit.CheckInUserToEvent;
import rocks.itsnotrocketscience.bejay.api.retrofit.CreateUser;
import rocks.itsnotrocketscience.bejay.api.retrofit.GetEvent;
import rocks.itsnotrocketscience.bejay.api.retrofit.GetEvents;
import rocks.itsnotrocketscience.bejay.api.retrofit.LoginUser;
import rocks.itsnotrocketscience.bejay.api.retrofit.PostSong;
import rocks.itsnotrocketscience.bejay.base.AppApplication;
import rocks.itsnotrocketscience.bejay.models.CmsUser;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;
import rocks.itsnotrocketscience.bejay.models.Token;

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

    public void getEventListFeed(EventListListener listener) {
        GetEvents events = restAdapter.create(GetEvents.class);

        events.getFeed("events", new Callback<ArrayList<Event>>() {
            @Override
            public void success(ArrayList<Event> eventList, Response response) {
                listener.onEventFeedLoaded(eventList, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onEventFeedLoaded(null, error);

            }
        });
    }

    public void checkoutUser(CheckoutListener listener, int id) {
        CheckInUserToEvent checkin = restAdapter.create(CheckInUserToEvent.class);
        checkin.checkOut(id, new Callback<Event>() {
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

    public void getEventFeed(EventListener listener, int id) {
        GetEvent event = restAdapter.create(GetEvent.class);

        event.getFeed(id, new Callback<Event>() {
            @Override
            public void success(Event eventList, Response response) {
                listener.onEventLoaded(eventList, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onEventLoaded(null, error);
            }
        });
    }

    public void registerUser(CmsUser user, RegisterListener listener) {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(Constants.API).build();
        CreateUser createUser = restAdapter.create(CreateUser.class);
        createUser.createUser(user, new Callback<CmsUser>() {
            @Override
            public void success(CmsUser user, Response response) {
                listener.onRegistered(user, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onRegistered(null, error);
            }
        });
    }


    public void loginUser(AuthCredentials auth, LoginListener listener) {
        LoginUser loginUser = restAdapter.create(LoginUser.class);

        loginUser.loginUser(Constants.TOKEN_AUTH, auth, new Callback<Token>() {
            @Override
            public void success(Token token, Response response) {
                listener.onLoggedIn(token, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onLoggedIn(null, error);
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
