package rocks.itsnotrocketscience.bejay.api;

import rocks.itsnotrocketscience.bejay.BuildConfig;

/**
 * Created by centralstation on 20/08/15.
 */
public class ApiConstants {
    public static final String BASE_API = "dukebox/";
    public static final String EVENTS = "events/";
    private static final String USERS = "users/";

    public static final String API = BuildConfig.BASE_URL + BASE_API;
    public static final String EVENTS_API = BuildConfig.BASE_URL + BASE_API + EVENTS;
    public static final String USER_API = BuildConfig.BASE_URL + BASE_API + USERS;
    public static final String TOKEN = "api-token-auth/";
}
