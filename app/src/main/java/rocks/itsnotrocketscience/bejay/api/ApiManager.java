package rocks.itsnotrocketscience.bejay.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.ServiceFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by centralstation on 22/10/15.
 *
 */
public class ApiManager {

    private static final int DEFAULT_RETRY_COUNT = 3;

    private final AccountManager accountManager;

    public ApiManager(Context context, AccountManager accountManager) {
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

    static Func1<Observable<? extends Throwable>, Observable<?>> defaultRetry() {
        return retry(DEFAULT_RETRY_COUNT);
    }

}
