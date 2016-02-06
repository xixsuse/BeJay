package rocks.itsnotrocketscience.bejay.gcm;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import javax.inject.Inject;

/**
 * Created by centralstation on 2/6/16.
 */
public class GcmUtils {
    private static final String TAG = "GcmUtils";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    SharedPreferences sharedPreferences;

    public GcmUtils(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    //returns ConnectionResult.SUCCESS of fail error code
    public boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");

            }
            return false;
        }
        return true;
    }

    public boolean hasRegistered() {
        return sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
    }
}