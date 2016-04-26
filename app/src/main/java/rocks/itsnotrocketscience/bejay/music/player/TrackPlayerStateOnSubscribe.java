package rocks.itsnotrocketscience.bejay.music.player;

import com.deezer.sdk.player.TrackPlayer;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by nemi on 26/04/16.
 */
class TrackPlayerStateOnSubscribe implements Observable.OnSubscribe<TrackPlayerState> {
    private final TrackPlayer trackPlayer;

    TrackPlayerStateOnSubscribe(TrackPlayer trackPlayer) {
        this.trackPlayer = trackPlayer;
    }

    @Override
    public void call(Subscriber<? super TrackPlayerState> subscriber) {
        TrackPlayerStateSubscription.subscribe(trackPlayer, subscriber);
    }
}
