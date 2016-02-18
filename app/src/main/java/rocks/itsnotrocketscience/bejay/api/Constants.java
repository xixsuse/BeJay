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
    String API = BuildConfig.BASE_URL;
    String TOKEN_AUTH = "api-token-auth";
    LogLevel RETROFIT_LOG_LEVEL = BuildConfig.RETROFIT_LOG_LEVEL;
}
