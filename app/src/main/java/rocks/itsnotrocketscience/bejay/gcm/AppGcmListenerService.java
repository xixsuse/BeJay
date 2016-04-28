package rocks.itsnotrocketscience.bejay.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import rocks.itsnotrocketscience.bejay.event.single.EventActivity;

public class AppGcmListenerService extends GcmListenerService {

    private static final String TAG = "AppGcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {

        if (from.startsWith("/topics/")) {
            Intent intent = new Intent(EventActivity.EVENT_RECEIVER_ID);
            intent.putExtras(data);
            this.sendBroadcast(intent);
        }
    }
}