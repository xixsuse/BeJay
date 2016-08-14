package rocks.itsnotrocketscience.bejay.event.single;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.dagger.ActivityModule;
import rocks.itsnotrocketscience.bejay.dagger.DaggerActivityComponent;
import rocks.itsnotrocketscience.bejay.gcm.RegistrationIntentService;

public class EventActivity extends InjectedActivity<ActivityComponent> {

    private static final String TAG = "EventActivity";
    public static final String EVENT_RECEIVER_ID = "event_receiver_id";
    public static final String EVENT_ID = "url_extra";

    @Inject
    private SharedPreferences sharedPreferences;

    @Bind(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.toolbar)
    private Toolbar toolbar;
    @Bind(R.id.app_bar_layout) AppBarLayout appBarLayout;

    private final ActivityModule activityModule;
    private ActivityComponent activityComponent;

    public EventActivity() {
        this.activityModule = new ActivityModule(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        subscribeToTopic();
        showFragment(EventFragment.newInstance());
    }

    @Override
    public ActivityComponent getComponent() {
        return activityComponent = DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .appComponent(getAppComponent())
                .build();
    }

    public int getIdFromBundle() {
        Bundle b = getIntent().getExtras();
        return b.getInt(EVENT_ID);
    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    private void showFragment(Fragment fragment) {
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
