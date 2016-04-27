package rocks.itsnotrocketscience.bejay.music.player;

import rocks.itsnotrocketscience.bejay.music.model.Track;

/**
 * Created by nemi on 27/04/16.
 */
public class EventPlaybackState extends PlaybackState {
    private final Track track;

    public EventPlaybackState(State state, long progress, Track track) {
        super(state, progress);
        this.track = track;
    }

    public Track getTrack() {
        return track;
    }

    @Override
    public String toString() {
        return "EventPlaybackState{" +
                "trackId=" + track.getId() +
                ", playbackState=" + super.toString() +
                '}';
    }
}
