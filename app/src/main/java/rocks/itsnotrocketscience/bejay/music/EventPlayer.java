package rocks.itsnotrocketscience.bejay.music;

import java.util.LinkedList;
import java.util.List;

import rocks.itsnotrocketscience.bejay.music.model.Track;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.observables.SyncOnSubscribe;
import rx.subjects.BehaviorSubject;

/**
 * Created by nemi on 17/04/16.
 */
public class EventPlayer {
    private BehaviorSubject<Observable<Track>> playlist = BehaviorSubject.create();
    private TrackPlayer trackPlayer;
    private Subscription playerStateSubscription;

    public EventPlayer(TrackPlayer trackPlayer) {
        this.trackPlayer = trackPlayer;
    }

    public void start() {
        playerStateSubscription = trackPlayer.state().filter(state -> state == TrackPlayer.STATE_STOPPED)
                .flatMap(state -> Observable.switchOnNext(playlist).first())
                .doOnUnsubscribe(trackPlayer::stop)
                .subscribe(track -> trackPlayer.play(track.getId()));
    }

    public void stop() {
        if(playerStateSubscription != null) {
            playerStateSubscription.unsubscribe();
            playerStateSubscription = null;
        }
    }

    public void play() {
        if(playerStateSubscription != null) {
            trackPlayer.play();
        }
    }

    public void pause() {
        if(playerStateSubscription != null) {
            trackPlayer.pause();
        }
    }

    public void skip() {
        if(playerStateSubscription != null) {
            trackPlayer.stop();
        }
    }

    public void onTracksChanged(List<Track> tracks) {
        playlist.onNext(trackQueue(tracks));
    }

    /**
     * Construct a stream that behaves like a queue. The stream is not re playable
     * */
    private Observable<Track> trackQueue(List<Track> trackList) {
        final LinkedList<Track> trackQueue = new LinkedList<>(trackList);
        return Observable.create(new SyncOnSubscribe<LinkedList<Track>, Track>() {
            @Override
            protected LinkedList<Track> generateState() {
                return trackQueue;
            }

            @Override
            protected LinkedList<Track> next(LinkedList<Track> state, Observer<? super Track> observer) {
                Track track = trackQueue.poll();
                if(track != null) {
                    observer.onNext(track);
                } else {
                    observer.onCompleted();
                }
                return trackQueue;
            }
        });
    }
}
