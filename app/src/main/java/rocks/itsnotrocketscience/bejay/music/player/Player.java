package rocks.itsnotrocketscience.bejay.music.player;

import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.PlayerState;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nemi on 25/04/16.
 */
public class Player {
    private final TrackPlayer trackPlayer;
    private final Observable<PlaybackState.State> playerStateObservable;
    private final Observable<Long> playerProgressObservable;

    public Player(TrackPlayer trackPlayer) {
        this.trackPlayer = trackPlayer;
        playerStateObservable = Observable.create(new PlayerStateOnSubscribe(trackPlayer))
                .filter(invalidStateFilter())
                .map(playerStateMapper())
                .unsubscribeOn(Schedulers.newThread());
        playerProgressObservable = Observable.create(new PlayerProgressOnSubscribe(trackPlayer))
                .unsubscribeOn(Schedulers.newThread());
    }

    private Func1<PlayerState, Boolean> invalidStateFilter() {
        return new Func1<PlayerState, Boolean>() {
            PlayerState oldState;
            @Override
            public Boolean call(PlayerState newState) {
                PlayerState oldState = this.oldState;
                this.oldState = newState;
                return oldState == null || !(oldState == PlayerState.STARTED && newState == PlayerState.STOPPED);
            }
        };
    }

    private Func1<PlayerState, PlaybackState.State> playerStateMapper() {
        return playerState -> {
            switch (playerState) {
                case STARTED:
                case INITIALIZING:
                case WAITING_FOR_DATA:
                case READY : {
                    return PlaybackState.State.BUFFERING;
                }
                case PAUSED: {
                    return PlaybackState.State.PAUSED;
                }
                case PLAYING : {
                    return PlaybackState.State.PLAYING;
                }
                case STOPPED:
                case PLAYBACK_COMPLETED: {
                    return PlaybackState.State.PLAYBACK_COMPETE;
                }
                default:
                    throw new IllegalArgumentException("unmapped player state " + playerState);
            }
        };
    }

    public Observable<PlaybackState> state() {
        return Observable.combineLatest(playerProgressObservable,
                playerStateObservable,
                (progress, playerState) -> new PlaybackState(playerState, progress))
                .distinct(state -> state.hashCode());
    }

    public void play(String trackId) {
        trackPlayer.playTrack(Long.parseLong(trackId));
    }

    public void pause() {
        trackPlayer.pause();
    }

    public void stop() {
        trackPlayer.stop();
        trackPlayer.release();
    }

}
