package rocks.itsnotrocketscience.bejay.api;

import retrofit.RestAdapter.LogLevel;
import rocks.itsnotrocketscience.bejay.BuildConfig;

/**
 * Created by centralstation on 28/09/15.
 */
public interface Constants {

    String TOKEN = "token";
    String EVENT_PK = "event_pk";
    String EMAIL = "email";
    String USERNAME = "username";
    String BASE_URL = BuildConfig.BASE_URL;
    String TOKEN_TYPE = "token_type";
    String REFRESH_TOKEN = "refresh_token";
    String IS_LOGGED_IN = "is_logged_in";
    String API = BuildConfig.BASE_URL + "dukebox/";
    LogLevel RETROFIT_LOG_LEVEL = BuildConfig.RETROFIT_LOG_LEVEL;
}
