package rocks.itsnotrocketscience.bejay.music.player;

import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.OnPlayerProgressListener;
import com.deezer.sdk.player.event.OnPlayerStateChangeListener;
import com.deezer.sdk.player.event.PlayerState;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by nemi on 25/04/16.
 */
class PlayerProgressSubscription implements OnPlayerProgressListener, OnPlayerStateChangeListener, Subscription {
    private final TrackPlayer trackPlayer;
    private Subscriber<? super Long> subscriber;

    public PlayerProgressSubscription(TrackPlayer trackPlayer, Subscriber<? super Long> subscriber) {
        this.trackPlayer = trackPlayer;
        this.subscriber = subscriber;
    }

    @Override
    public void onPlayerProgress(long progress) {
        if (!isUnsubscribed()) {
            subscriber.onNext(progress);
        }
    }

    @Override
    public void onPlayerStateChange(PlayerState playerState, long l) {
        if(complete(playerState)) {
            subscriber.onCompleted();
        }
    }

    private boolean complete(PlayerState playerState) {
        return !isUnsubscribed() && playerState == PlayerState.RELEASED;
    }

    @Override
    public void unsubscribe() {
        trackPlayer.removeOnPlayerProgressListener(this);
        trackPlayer.removeOnPlayerStateChangeListener(this);
    }

    @Override
    public boolean isUnsubscribed() {
        return subscriber.isUnsubscribed();
    }
}
