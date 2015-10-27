package rocks.itsnotrocketscience.bejay.managers;

import android.app.Activity;
import android.content.Intent;

import rocks.itsnotrocketscience.bejay.event.single.EventActivity;

/**
 * Created by centralstation on 27/10/15.
 *
 */
public class LaunchManager {


    public LaunchManager() { }


    public static void launchEvent(int id, Activity activity) {
        Intent intent = new Intent(activity, EventActivity.class);
        intent.putExtra(EventActivity.EVENT_ID, id);
        activity.startActivity(intent);
    }

}
