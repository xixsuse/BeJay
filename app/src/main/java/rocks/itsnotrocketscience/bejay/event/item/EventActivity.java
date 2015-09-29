package rocks.itsnotrocketscience.bejay.event.item;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import rocks.itsnotrocketscience.bejay.R;

public class EventActivity extends AppCompatActivity {

    public static String URL_EXTRA = "url_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getIdFromBundle();

    }

    public int getIdFromBundle() {
        Bundle b = getIntent().getExtras();
        return b.getInt(URL_EXTRA);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
