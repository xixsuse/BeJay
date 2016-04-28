package rocks.itsnotrocketscience.bejay.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.base.AppApplication;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.RetrofitListeners;
import rocks.itsnotrocketscience.bejay.managers.ServiceFactory;
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
                .setLogLevel(Constants.RETROFIT_LOG_LEVEL)
                .setEndpoint(Constants.API)
                .build();
        this.accountManager = accountManager;
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
