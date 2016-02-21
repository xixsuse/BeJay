package rocks.itsnotrocketscience.bejay.event.single;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.gcm.GcmPubSub;

import java.io.IOException;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;
import rocks.itsnotrocketscience.bejay.gcm.RegistrationIntentService;

public class EventActivity extends BaseActivity {

    private static final String TAG = "EventActivity";
    public static final String EVENT_RECEIVER_ID = "event_receiver_id";
    public static String EVENT_ID = "url_extra";

    @Inject SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        subscribeToTopic();
        showFragment(EventFragment.newInstance());

    }

    public int getIdFromBundle() {
        Bundle b = getIntent().getExtras();
        return b.getInt(EVENT_ID);
    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    public void showFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void subscribeToTopic() {
        String token = sharedPreferences.getString(RegistrationIntentService.GCM_TOKEN, null);
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        int id = getIdFromBundle();
        Thread thread = new Thread(() -> {
            if (token != null && id >= 0) {
                try {
                    pubSub.subscribe(token, "/topics/" + id, null);
                    Log.d(TAG, "subscribeTopics: sucess");
                } catch (IOException e) {
                    Log.d(TAG, "subscribeTopics: fail");
                }
            }
        });
        thread.start();
    }
}
