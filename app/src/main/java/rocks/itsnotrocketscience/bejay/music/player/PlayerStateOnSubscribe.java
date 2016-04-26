package rocks.itsnotrocketscience.bejay.music.player;

import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.PlayerState;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by nemi on 25/04/16.
 */
class PlayerStateOnSubscribe implements Observable.OnSubscribe<PlayerState> {
    private final TrackPlayer trackPlayer;

    public PlayerStateOnSubscribe(TrackPlayer trackPlayer) {
        this.trackPlayer = trackPlayer;
    }

    @Override
    public void call(Subscriber<? super PlayerState> subscriber) {
        PlayerStateSubscription s = new PlayerStateSubscription(trackPlayer, subscriber);
        if (!s.isUnsubscribed()) {
            subscriber.onNext(trackPlayer.getPlayerState());
            trackPlayer.addOnPlayerStateChangeListener(s);
            subscriber.add(s);
        }
    }
}
