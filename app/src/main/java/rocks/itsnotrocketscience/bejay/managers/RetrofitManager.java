package rocks.itsnotrocketscience.bejay.managers;

import android.content.Context;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.api.retrofit.PostSong;
import rocks.itsnotrocketscience.bejay.base.AppApplication;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by centralstation on 22/10/15.
 */
public class RetrofitManager extends RetrofitListeners {

    static final int DEFAULT_RETRY_COUNT = 3;

    RestAdapter restAdapter;
    AppApplication application;
    AccountManager accountManager;

    public RetrofitManager(Context context, AccountManager accountManager) {

        application = (AppApplication) context.getApplicationContext();
        restAdapter = new RestAdapter.Builder()
                .setRequestInterceptor(accountManager.getAuthTokenInterceptor())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.API)
                .build();
        this.accountManager = accountManager;
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


    public Events events() {
        return new EventsWithRetry(ServiceFactory.createRetrofitServiceAuth(Events.class, accountManager.getAuthTokenInterceptor()));
    }

    private static Func1<Observable<? extends Throwable>, Observable<?>> retry(final int count) {
        return retry -> Observable.zip(Observable.range(1, count + 1), retry, (retryCount, error) -> {
            if (retryCount <= count) {
                return Observable.timer(retryCount, TimeUnit.SECONDS);
            }
            return Observable.error(error);
        }).flatMap(observable -> observable);
    }

    private static Func1<Observable<? extends Throwable>, Observable<?>> defaultRetry() {
        return retry(DEFAULT_RETRY_COUNT);
    }

    private static class EventsWithRetry implements Events {

        private final Events events;

        private EventsWithRetry(Events events) {
            this.events = events;
        }

        @Override
        @GET("/events")
        public Observable<ArrayList<Event>> list() {
            return events.list().retryWhen(defaultRetry());
        }

        @Override
        @GET("/events/{id}")
        public Observable<Event> list(@Path("id") int id) {
            return events.list(id).retryWhen(defaultRetry());
        }

        @Override
        @POST("/events/{id}/checkin_user/")
        public Observable<Event> checkIn(@Path("id") int id) {
            return events.checkIn(id).retryWhen(defaultRetry());
        }

        @Override
        @POST("/events/{id}/checkout_user/")
        public Observable<Event> checkOut(@Path("id") int id) {
            return events.checkOut(id).retryWhen(defaultRetry());
        }
    }
}
