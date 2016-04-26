package rocks.itsnotrocketscience.bejay.music.player;

import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.PlayerState;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
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
    private final Observable<PlaybackState> playbackState;

    public Player(TrackPlayer trackPlayer) {
        this.trackPlayer = trackPlayer;
        this.playbackState = Observable.concat(initialState(),
                Observable.create(new TrackPlayerStateOnSubscribe(trackPlayer))
                        .filter(invalidStateFilter())
                        .filter(state -> RECOGNIZED_STATES.contains(state.getPlayerState()))
                        .map(state -> new PlaybackState(
                                STATE_MAP.get(state.getPlayerState()), state.getProgress()))
                        .unsubscribeOn(Schedulers.newThread()));
    }

    private Func1<TrackPlayerState, Boolean> invalidStateFilter() {
        return new Func1<TrackPlayerState, Boolean>() {
            PlayerState currentState;

            @Override
            public Boolean call(TrackPlayerState newState) {
                PlayerState oldState = currentState;
                currentState = newState.getPlayerState();
                boolean allowed = !((oldState == null || oldState == PlayerState.PLAYBACK_COMPLETED) && currentState == PlayerState.STOPPED) && !currentState.equals(oldState);
                return allowed;
            }
        };
    }

    private Observable<PlaybackState> initialState() {
        return Observable.just(new PlaybackState(PlaybackState.State.STARTED, 0));
    }

    public Observable<PlaybackState> state() {
        return playbackState;
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
