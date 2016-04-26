package rocks.itsnotrocketscience.bejay.music.player;

/**
 * Created by nemi on 25/04/16.
 */
public class PlaybackState {
    public enum State {
        STARTED, BUFFERING, PLAYING, PAUSED, PLAYBACK_COMPETE
    }

    private final State state;
    private final long progress;

    public State getState() {
        return state;
    }

    public long getProgress() {
        return progress;
    }

    public PlaybackState(State state, long progress) {
        this.state = state;
        this.progress = progress;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlaybackState playbackState = (PlaybackState) o;

        return progress == playbackState.progress && state == playbackState.state;
    }

    @Override
    public int hashCode() {
        int result = state != null ? state.hashCode() : 0;
        result = 31 * result + (int) (progress ^ (progress >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PlaybackState{" +
                "state=" + state +
                ", progress=" + progress +
                '}';
    }
}
