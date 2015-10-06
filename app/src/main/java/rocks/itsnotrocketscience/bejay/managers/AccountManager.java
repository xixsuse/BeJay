package rocks.itsnotrocketscience.bejay.managers;

import android.content.SharedPreferences;

import rocks.itsnotrocketscience.bejay.api.Constants;

/**
 * Created by centralstation on 11/09/15.
 */
public class AccountManager {

    SharedPreferences sharedPreferences;

    public AccountManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;

    }

    public String getTokenAuth(){
        return sharedPreferences.getString(Constants.TOKEN, "");
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getString(Constants.TOKEN, "") != "";
    }

    public void clearLogin(){
        sharedPreferences.edit().putString(Constants.TOKEN, "").apply();
    }

    public void setCheckedIn(int pk) {
        sharedPreferences.edit().putInt(Constants.EVENT_PK, pk).apply();
    }
    public boolean isCheckedIn(){
        return sharedPreferences.getInt(Constants.EVENT_PK, -1) != -1;
    }

    public int getCheckedInEventPk(){
        return sharedPreferences.getInt(Constants.EVENT_PK, -1);
    }

}
