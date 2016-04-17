package rocks.itsnotrocketscience.bejay.music;


import com.deezer.sdk.player.event.OnPlayerStateChangeListener;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nemi on 17/04/16.
 */
public class TrackPlayer {
    public static final int STATE_STOPPED = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_BUFFERING = 3;

    private final com.deezer.sdk.player.TrackPlayer player;
    private final BehaviorSubject<Integer> playerState = BehaviorSubject.create(STATE_STOPPED);
    private final OnPlayerStateChangeListener onPlayerStateChangeListener = (playerState, l) -> {
        switch (playerState) {
            case PLAYING : {
                TrackPlayer.this.playerState.onNext(STATE_PLAYING);
                break;
            }
            case PAUSED : {
                TrackPlayer.this.playerState.onNext(STATE_PAUSED);
                break;
            }
            case PLAYBACK_COMPLETED : {
                TrackPlayer.this.playerState.onNext(STATE_STOPPED);
                break;
            }
            case WAITING_FOR_DATA : {
                TrackPlayer.this.playerState.onNext(STATE_BUFFERING);
                break;
            }
        }
    };

    public TrackPlayer(com.deezer.sdk.player.TrackPlayer player) {
        this.player = player;
        this.player.addOnPlayerStateChangeListener(onPlayerStateChangeListener);
    }

    public void play(String trackId) {
        player.playTrack(Long.parseLong(trackId));
    }

    public void play() {
        player.play();
    }

    public void pause() {
        player.pause();
    }

    public void stop() {
        player.stop();
        player.release();
    }

    public Observable<Integer> state() {
        return playerState;
    }
}
