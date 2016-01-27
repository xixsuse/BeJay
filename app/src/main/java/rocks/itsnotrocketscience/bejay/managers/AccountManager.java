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
 */
public class AccountManager {

    SharedPreferences sharedPreferences;

    public AccountManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

    }

    public String getAuthToken() {
        return sharedPreferences.getString(Constants.TOKEN, "");
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean isLoggedIn() {
        return !Objects.equals(sharedPreferences.getString(Constants.TOKEN, ""), "");
    }

    public void clearLogin() {
        sharedPreferences.edit().putString(Constants.TOKEN, "").apply();
    }

    public boolean isCheckedIn() {
        return sharedPreferences.getInt(Constants.EVENT_PK, -1) != -1;
    }

    public void setCheckedIn(int pk) {
        sharedPreferences.edit().putInt(Constants.EVENT_PK, pk).apply();
    }

    public int getCheckedInEventId() {
        return sharedPreferences.getInt(Constants.EVENT_PK, -1);
    }

    public void clearCheckin() {
        sharedPreferences.edit().putString(Constants.EVENT_PK, null).apply();
    }

    public RequestInterceptor getAuthTokenInterceptor() {
        return new AuthInterceptor(getAuthToken());
    }

}
