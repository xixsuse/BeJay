package rocks.itsnotrocketscience.bejay.music.player;

import android.os.Handler;
import android.util.Log;

import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.PlayerState;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by nemi on 25/04/16.
 */
public class Player {
    private static final EnumSet<PlayerState> RECOGNIZED_STATES = EnumSet.of(
            PlayerState.WAITING_FOR_DATA,
            PlayerState.PAUSED,
            PlayerState.PLAYING,
            PlayerState.STOPPED,
            PlayerState.PLAYBACK_COMPLETED);

    private static final Map<PlayerState, PlaybackState.State> STATE_MAP
            = new HashMap<PlayerState, PlaybackState.State>() {
        {
            put(PlayerState.INITIALIZING,       PlaybackState.State.BUFFERING);
            put(PlayerState.WAITING_FOR_DATA,   PlaybackState.State.BUFFERING);
            put(PlayerState.PAUSED,             PlaybackState.State.PAUSED);
            put(PlayerState.PLAYING,            PlaybackState.State.PLAYING);
            put(PlayerState.STOPPED,            PlaybackState.State.PLAYBACK_COMPETE);
            put(PlayerState.PLAYBACK_COMPLETED, PlaybackState.State.PLAYBACK_COMPETE);
        }
    };

    private final TrackPlayer trackPlayer;

    public Player(TrackPlayer trackPlayer) {
        this.trackPlayer = trackPlayer;
    }

    private Func1<TrackPlayerState, Boolean> invalidStateFilter() {
        return new Func1<TrackPlayerState, Boolean>() {
            TrackPlayerState currentState;

            @Override
            public Boolean call(TrackPlayerState newState) {
                TrackPlayerState oldState = currentState;
                currentState = newState;
                boolean allowed = !((oldState == null || oldState.getPlayerState() == PlayerState.PLAYBACK_COMPLETED) &&
                        currentState.getPlayerState() == PlayerState.STOPPED) &&
                        RECOGNIZED_STATES.contains(currentState.getPlayerState()) &&
                        !currentState.equals(oldState);
                return allowed;
            }
        };
    }

    private Observable<PlaybackState> initialState() {
        return Observable.just(new PlaybackState(PlaybackState.State.STARTED, 0));
    }

    public Observable<PlaybackState> state() {
        return Observable.concat(initialState(),
                Observable.create(new TrackPlayerStateOnSubscribe(trackPlayer))
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .filter(invalidStateFilter())
                        .map(state -> new PlaybackState(
                                STATE_MAP.get(state.getPlayerState()), state.getProgress())));
    }

    public void play(String trackId) {
        trackPlayer.playTrack(Long.parseLong(trackId));
    }

    public void pause() {
        trackPlayer.pause();
    }

    public void skip() {
        trackPlayer.stop();
    }

    public void stop() {
        trackPlayer.stop();
        trackPlayer.release();
    }

}
