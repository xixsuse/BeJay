package rocks.itsnotrocketscience.bejay.music.player;

import java.util.EnumSet;
import java.util.List;

import rocks.itsnotrocketscience.bejay.music.model.Track;
import rx.Observable;
import rx.functions.Func2;
import rx.subjects.BehaviorSubject;

/**
 * Created by nemi on 26/04/16.
 */
public class EventPlayer {
    private final Player player;
    private final BehaviorSubject<Observable<Track>> trackQueue = BehaviorSubject.create();
    private final Observable<Track> tracks = Observable.switchOnNext(trackQueue);
    private final BehaviorSubject<EventPlaybackState> eventPlaybackState = BehaviorSubject.create();
    private final BehaviorSubject<Track> nextTrack = BehaviorSubject.create();

    public EventPlayer(Player player) {
        this.player = player;
    }

    public void setPlaylist(List<Track> trackList) {
        trackQueue.onNext(TrackQueue.trackQueue(trackList));
    }

    public void start() {
        player.state()
                .filter(playerState -> EnumSet.of(PlaybackState.State.PLAYBACK_COMPETE, PlaybackState.State.STARTED).contains(playerState.getState()))
                .flatMap(state -> tracks().first())
                .subscribe(nextTrack);
        nextTrack.subscribe(track -> player.play(track.getId()));
        Observable.combineLatest(player.state(), nextTrack,
                (playbackState, track) ->
                        new EventPlaybackState(playbackState.getState(),
                                playbackState.getProgress(), track)).subscribe(eventPlaybackState);
    }

    private Observable<Track> tracks() {
        return tracks;
    }
}
