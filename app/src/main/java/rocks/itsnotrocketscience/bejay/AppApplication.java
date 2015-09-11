package rocks.itsnotrocketscience.bejay;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by centralstation on 11/09/15.
 */
public class AppApplication extends Application {

    public SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
    }
}
