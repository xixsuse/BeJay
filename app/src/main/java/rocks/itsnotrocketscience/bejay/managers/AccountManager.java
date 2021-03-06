package rocks.itsnotrocketscience.bejay.managers;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Objects;

import retrofit.RequestInterceptor;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.AuthInterceptor;

/**
 * Created by centralstation on 11/09/15.
 *
 */
public class AccountManager {

    public static final int EVENT_NONE = -1;

    private final SharedPreferences sharedPreferences;

    public AccountManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

    }

    private String getAuthToken() {
        return sharedPreferences.getString(Constants.TOKEN, "");
    }

    public boolean isCheckedIn() {
        return sharedPreferences.getInt(Constants.EVENT_PK, -1) != -1;
    }

    public void setCheckedIn(int pk) {
        sharedPreferences.edit().putInt(Constants.EVENT_PK, pk).apply();
    }

    public int getCheckedInEventId() {
        return sharedPreferences.getInt(Constants.EVENT_PK, EVENT_NONE);
    }

    public RequestInterceptor getAuthTokenInterceptor() {
        return new AuthInterceptor(getAuthToken());
    }

}
