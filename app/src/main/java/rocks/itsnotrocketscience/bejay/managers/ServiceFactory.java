package rocks.itsnotrocketscience.bejay.managers;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rocks.itsnotrocketscience.bejay.api.Constants;

/**
 * Created by lduf0001 on 25/01/16.
 */

public class ServiceFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     *
     * @param clazz    Java interface of the retrofit service
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API)
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }

    public static <T> T createRetrofitServiceAuth(final Class<T> clazz, RequestInterceptor authInterceptor) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(authInterceptor)
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }

    public static <T> T createGcmRetrofitService(final Class<T> clazz) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }
}

