package rocks.itsnotrocketscience.bejay.managers;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rocks.itsnotrocketscience.bejay.api.Constants;

/**
 * Created by lduf0001 on 25/01/16.
 */

public class ServiceFactory {

    public static <T> T createRetrofitServiceAuth(final Class<T> clazz, RequestInterceptor authInterceptor) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(authInterceptor)
                .build();

        return restAdapter.create(clazz);
    }

    public static <T> T createRetrofitServiceNoAuth(final Class<T> clazz) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter.create(clazz);
    }
}

