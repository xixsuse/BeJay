package rocks.itsnotrocketscience.bejay.music.player;

import com.deezer.sdk.player.TrackPlayer;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by nemi on 25/04/16.
 */
class PlayerProgressOnSubscribe implements Observable.OnSubscribe<Long> {
    private final TrackPlayer trackPlayer;

    public PlayerProgressOnSubscribe(TrackPlayer trackPlayer) {
        this.trackPlayer = trackPlayer;
    }

    @Override
    public void call(Subscriber<? super Long> subscriber) {
        PlayerProgressSubscription s = new PlayerProgressSubscription(trackPlayer, subscriber);
        if(!s.isUnsubscribed()) {
            trackPlayer.addOnPlayerProgressListener(s);
            trackPlayer.addOnPlayerStateChangeListener(s);
            subscriber.onNext(trackPlayer.getPosition());
            subscriber.add(s);
        }
    }
}
