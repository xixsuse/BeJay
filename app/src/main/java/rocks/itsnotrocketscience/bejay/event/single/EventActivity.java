package rocks.itsnotrocketscience.bejay.event.single;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;

public class EventActivity extends BaseActivity {

    public static String EVENT_ID = "url_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(EventFragment.newInstance());

    }

    public int getIdFromBundle() {
        Bundle b = getIntent().getExtras();
        return b.getInt(EVENT_ID);
    }

    public void setTitle(String title){
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

}
