package rocks.itsnotrocketscience.bejay.music.player;

import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.OnPlayerProgressListener;
import com.deezer.sdk.player.event.OnPlayerStateChangeListener;
import com.deezer.sdk.player.event.PlayerState;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by nemi on 26/04/16.
 */
class TrackPlayerStateSubscription implements OnPlayerStateChangeListener, OnPlayerProgressListener, Subscription {
    private final TrackPlayer trackPlayer;
    private final Subscriber<? super TrackPlayerState> subscriber;
    private final TrackPlayerState.Builder trackPlayerState = new TrackPlayerState.Builder();

    TrackPlayerStateSubscription(TrackPlayer trackPlayer,
                                        Subscriber<? super TrackPlayerState> subscriber) {
        this.trackPlayer = trackPlayer;
        this.subscriber = subscriber;
    }

    static void subscribe(TrackPlayer trackPlayer, Subscriber<? super TrackPlayerState> subscriber) {
        if(!subscriber.isUnsubscribed()) {
            TrackPlayerStateSubscription s = new TrackPlayerStateSubscription(trackPlayer, subscriber);
            trackPlayer.addOnPlayerStateChangeListener(s);
            trackPlayer.addOnPlayerProgressListener(s);
            subscriber.add(s);
        }
    }


    @Override
    public void onPlayerProgress(long l) {
        if(!isUnsubscribed()) {
            subscriber.onNext(trackPlayerState.setProgress(l).build());
        }
    }

    @Override
    public void onPlayerStateChange(PlayerState playerState, long l) {
        if(!isUnsubscribed()) {
            if(playerState == PlayerState.RELEASED) {
                subscriber.onCompleted();
            } else {
                subscriber.onNext(trackPlayerState.setPlayerState(playerState).setProgress(l).build());
            }
        }
    }

    @Override
    public void unsubscribe() {
        trackPlayer.removeOnPlayerStateChangeListener(this);
        trackPlayer.removeOnPlayerProgressListener(this);
    }

    @Override
    public boolean isUnsubscribed() {
        return subscriber.isUnsubscribed();
    }
}
