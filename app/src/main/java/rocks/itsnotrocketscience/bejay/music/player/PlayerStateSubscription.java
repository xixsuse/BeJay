package rocks.itsnotrocketscience.bejay.music.player;

import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.OnPlayerStateChangeListener;
import com.deezer.sdk.player.event.PlayerState;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by nemi on 25/04/16.
 */
class PlayerStateSubscription implements OnPlayerStateChangeListener, Subscription {
    private final TrackPlayer trackPlayer;
    private final Subscriber<? super PlayerState> subscriber;

    PlayerStateSubscription(TrackPlayer trackPlayer, Subscriber<? super PlayerState> subscriber) {
        this.trackPlayer = trackPlayer;
        this.subscriber = subscriber;
    }

    @Override
    public void onPlayerStateChange(PlayerState playerState, long l) {
        if (!isUnsubscribed()) {
            handleOnPlayerStateChanged(playerState);
        }
    }

    private void handleOnPlayerStateChanged(PlayerState playerState) {
        if(isPlayerReleased()) {
            subscriber.onCompleted();
        } else {
            subscriber.onNext(playerState);
        }
    }

    @Override
    public void unsubscribe() {
        trackPlayer.removeOnPlayerStateChangeListener(this);
    }

    private boolean isPlayerReleased() {
        return trackPlayer.getPlayerState() == PlayerState.RELEASED;
    }


    @Override
    public boolean isUnsubscribed() {
        return subscriber.isUnsubscribed();
    }
}
