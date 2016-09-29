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

    int SUCCESS_RESULT = 0;

    int FAILURE_RESULT = 1;

    String PACKAGE_NAME = "rocks.itsnotrocketscience.bejay.map";

    String RECEIVER = PACKAGE_NAME + ".RECEIVER";

    String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    String RESULT_CODE = "result_code";
}
