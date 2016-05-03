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
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.dagger.ActivityModule;
import rocks.itsnotrocketscience.bejay.dagger.DaggerActivityComponent;
import rocks.itsnotrocketscience.bejay.event.manager.Event;
import rocks.itsnotrocketscience.bejay.event.manager.EventException;
import rocks.itsnotrocketscience.bejay.event.manager.Resolution;
import rocks.itsnotrocketscience.bejay.event.manager.service.EventService;
import rocks.itsnotrocketscience.bejay.gcm.RegistrationIntentService;

import rocks.itsnotrocketscience.bejay.music.model.Track;
import rx.Observable;
import rx.subjects.PublishSubject;

public class EventActivity extends InjectedActivity<ActivityComponent> {

    private static final String TAG = "EventActivity";
    public static final String EVENT_RECEIVER_ID = "event_receiver_id";
    public static String EVENT_ID = "url_extra";

    @Inject SharedPreferences sharedPreferences;

    @Bind(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.app_bar_layout) AppBarLayout appBarLayout;

    ActivityModule activityModule;
    ActivityComponent activityComponent;

    private EventFragment eventFragment;
    private Event event;
    private final PublishSubject<Boolean> onDestroy = PublishSubject.create();

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
        eventFragment = EventFragment.newInstance();
        showFragment(eventFragment);
        startEvent();
    }

    private void startEvent() {
        EventService.start(this).compose(getOnDestroyTransformer()).subscribe(event -> {
            onEventStared(event);
        }, error -> {
            if(isRecoverableEventError(error)) {
                startResolution(((EventException)error).getResolution());
            } else {
                finish();
            }
        });
    }

    private void startResolution(Resolution resolution) {        //TODO: invoke injection logic

        resolution.resolve(this).compose(getOnDestroyTransformer()).subscribe(resolutionResult -> {
            switch (resolutionResult) {
                case SUCCESS: {
                    startEvent();
                    break;
                }
                case ERROR : {
                    finish();
                    break;
                }
            }
        });
    }

    Track newTrack(long id) {
        Track track = new Track();
        track.setId(Long.toString(id));
        return track;
    }

    List<Track> newTrackList(long... ids) {
        ArrayList<Track> trackList = new ArrayList<>();
        for(long id : ids) {
            trackList.add(newTrack(id));
        }
        return trackList;
    }

    private void onEventStared(Event event) {
        event.getEventPlayer().start();
        event.getEventPlayer().setPlaylist(newTrackList(87489547, 123182590, 120402256, 120572554));
    }

    protected <T> Observable.Transformer<T, T> getOnDestroyTransformer() {
        return source -> source.takeUntil(onDestroy);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroy.onNext(true);
    }

    private boolean isRecoverableEventError(Throwable error) {
        return error instanceof EventException && ((EventException)error).isRecoverable();
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
