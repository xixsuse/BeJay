package rocks.itsnotrocketscience.bejay.music.player;

import com.deezer.sdk.player.event.PlayerState;

/**
 * Created by nemi on 26/04/16.
 */
class TrackPlayerState {
    private final PlayerState playerState;
    private final long progress;

    static class Builder {
        private PlayerState playerState = PlayerState.STOPPED;
        private long progress = 0;

        public Builder setPlayerState(PlayerState playerState) {
            this.playerState = playerState;
            return this;
        }

        public Builder setProgress(long progress) {
            this.progress = progress;
            return this;
        }

        public TrackPlayerState build() {
            return new TrackPlayerState(playerState, progress);
        }
    }

    TrackPlayerState(PlayerState playerState, long progress) {
        this.playerState = playerState;
        this.progress = progress;
    }

    PlayerState getPlayerState() {
        return playerState;
    }

    long getProgress() {
        return progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackPlayerState that = (TrackPlayerState) o;

        if (progress != that.progress) return false;
        return playerState == that.playerState;

    }

    @Override
    public int hashCode() {
        int result = playerState != null ? playerState.hashCode() : 0;
        result = 31 * result + (int) (progress ^ (progress >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "TrackPlayerState{" +
                "playerState=" + playerState +
                ", progress=" + progress +
                '}';
    }
}
