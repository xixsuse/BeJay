package rocks.itsnotrocketscience.bejay.music;


import android.util.Log;

import com.deezer.sdk.player.event.OnPlayerProgressListener;
import com.deezer.sdk.player.event.OnPlayerStateChangeListener;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by nemi on 17/04/16.
 */
public class TrackPlayer {
    private static final String TAG = "TrackPlayer";

    public static final int STATE_STOPPED = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_BUFFERING = 3;

    public static class State {
        private final int playerState;
        private final long playerProgress;

        public State(int state, long progress) {
            this.playerState = state;
            this.playerProgress = progress;
        }

        public int getPlayerState() {
            return playerState;
        }

        public long getPlayerProgress() {
            return playerProgress;
        }

        @Override
        public boolean equals(Object o) {
            if(o == null || (o instanceof State)) {
                return false;
            }
            State that = (State) o;
            return this.playerState == that.playerState && this.playerProgress == that.playerProgress;
        }

        @Override
        public int hashCode() {
            int result = playerState;
            result = 31 * result + (int) (playerProgress ^ (playerProgress >>> 32));
            return result;
        }

        @Override
        public String toString() {
            return "State{" +
                    "playerState=" + playerState +
                    ", playerProgress=" + playerProgress +
                    '}';
        }
    }

    private final com.deezer.sdk.player.TrackPlayer player;

    private final BehaviorSubject<Integer> playerState = BehaviorSubject.create(STATE_STOPPED);
    private final BehaviorSubject<Long> playerProgress = BehaviorSubject.create(0l);
    private final Observable<State> state = Observable.combineLatest(playerState, playerProgress,
            (state, progress) -> new State(state, progress)).distinct(state -> state.hashCode());

    private final OnPlayerProgressListener onPlayerProgressListener
            = (progress) -> playerProgress.onNext(progress);

    private final OnPlayerStateChangeListener onPlayerStateChangeListener = (state, progress) -> {
        Log.i(TAG, "onStateChanged: state=="+state);
        switch (state) {
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
        this.player.addOnPlayerProgressListener(onPlayerProgressListener);
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

    public Observable<State> state() {
        return state;
    }
}
