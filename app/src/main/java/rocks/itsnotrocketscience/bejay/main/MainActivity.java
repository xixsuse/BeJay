package rocks.itsnotrocketscience.bejay.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.appevents.AppEventsLogger;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;
import rocks.itsnotrocketscience.bejay.gcm.GcmUtils;
import rocks.itsnotrocketscience.bejay.gcm.RegistrationIntentService;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}