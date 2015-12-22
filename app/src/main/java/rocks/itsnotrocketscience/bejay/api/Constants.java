package rocks.itsnotrocketscience.bejay.api;

import rocks.itsnotrocketscience.bejay.BuildConfig;

/**
 * Created by centralstation on 28/09/15.
 */
public interface Constants {

    String TOKEN = "token";
    String EVENT_PK = "event_pk";
    String EMAIL = "email";
    String USERNAME = "username";
    String BASE_API = "dukebox/";
    String API = BuildConfig.BASE_URL + BASE_API;
    String TOKEN_AUTH = "api-token-auth";
}
