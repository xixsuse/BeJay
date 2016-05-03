package rocks.itsnotrocketscience.bejay.event.manager.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.event.manager.DeezerAuthorizationResolution;
import rocks.itsnotrocketscience.bejay.event.manager.Event;
import rocks.itsnotrocketscience.bejay.event.manager.EventException;
import rocks.itsnotrocketscience.bejay.music.player.EventPlayer;
import rocks.itsnotrocketscience.bejay.music.player.Player;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by nemi on 02/05/16.
 */
public class EventService extends Service {


    private DeezerConnect deezerConnect;
    private SessionStore sessionStore;

    private Event event;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new EventBinder(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        deezerConnect = new DeezerConnect(this, getString(R.string.deezer_app_id));
        sessionStore = new SessionStore();
    }

    Event getEvent() throws Exception {
        if(hasEvent()) {
            return event;
        }
        event = newEvent();
        return event;
    }

    private Event newEvent() throws Exception {
        return new Event(newEventPlayer());
    }

    private EventPlayer newEventPlayer() throws Exception {
        if(!isDeezerSessionValid()) {
            throw new EventException(new DeezerAuthorizationResolution(deezerConnect, sessionStore));
        }

        TrackPlayer trackPlayer = new TrackPlayer(getApplication(), deezerConnect, new WifiAndMobileNetworkStateChecker());
        Player player = new Player(trackPlayer);
        return new EventPlayer(player);
    }

    private boolean hasEvent() {
        return event != null;
    }

    private boolean isDeezerSessionValid() {
        return !deezerConnect.isSessionValid() || !sessionStore.restore(deezerConnect, this);
    }

    public static Observable<Event> start(Context context) {
        return Observable.create(new ServiceOnSubscribe(context))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(AndroidSchedulers.mainThread());
    }
}
