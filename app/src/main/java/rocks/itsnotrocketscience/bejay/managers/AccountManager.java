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
}
