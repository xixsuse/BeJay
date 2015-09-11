package rocks.itsnotrocketscience.bejay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rocks.itsnotrocketscience.bejay.api.ApiConstants;
import rocks.itsnotrocketscience.bejay.api.GetEvents;
import rocks.itsnotrocketscience.bejay.models.Event;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFeed();
    }

    private void getFeed() {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConstants.API).build();
        GetEvents events = restAdapter.create(GetEvents.class);

        events.getFeed("events",new Callback<ArrayList<Event>>() {
            @Override
            public void success(ArrayList<Event> gitmodel, Response response) {
                Log.d("yo", "yo");
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d("yo", "yo");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
