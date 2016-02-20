package rocks.itsnotrocketscience.bejay.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.api.retrofit.PostSong;
import rocks.itsnotrocketscience.bejay.base.AppApplication;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.RetrofitListeners;
import rocks.itsnotrocketscience.bejay.managers.ServiceFactory;
import rocks.itsnotrocketscience.bejay.models.Song;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by centralstation on 22/10/15.
 */
public class ApiManager extends RetrofitListeners {

    static final int DEFAULT_RETRY_COUNT = 3;

    RestAdapter restAdapter;
    AppApplication application;
    AccountManager accountManager;

    public ApiManager(Context context, AccountManager accountManager) {

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

    public static Func1<Observable<? extends Throwable>, Observable<?>> defaultRetry() {
        return retry(DEFAULT_RETRY_COUNT);
    }

}
