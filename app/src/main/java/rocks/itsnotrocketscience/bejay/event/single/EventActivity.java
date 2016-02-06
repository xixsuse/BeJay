package rocks.itsnotrocketscience.bejay.event.single;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;
import rocks.itsnotrocketscience.bejay.gcm.QuickstartPreferences;

public class EventActivity extends BaseActivity {

    private static final String TAG = "EventActivity";
    public static String EVENT_ID = "url_extra";
    @Inject SharedPreferences sharedPreferences;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppApplication().getNetComponent().inject(this);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.d("woo hoo", "woo hoo");
                } else {
                    Log.d("boo hoo", "boo hoo");
                }
            }
        };
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
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}
