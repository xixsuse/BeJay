package rocks.itsnotrocketscience.bejay.base;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import rocks.itsnotrocketscience.bejay.managers.AccountManager;

/**
 * Created by centralstation on 11/09/15.
 *
 */
public class AppApplication extends Application {
    private static AccountManager accountManager;

    public SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
    }

    public AccountManager getAccountManager(){
        if(accountManager==null){
            return new AccountManager(PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext()));
        }
        return accountManager;
    }
}
