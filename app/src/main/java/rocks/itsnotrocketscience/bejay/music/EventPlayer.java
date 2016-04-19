package rocks.itsnotrocketscience.bejay.music;

import android.util.Log;

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
    private static final String TAG = "EventPlayer";

    private final BehaviorSubject<Observable<Track>> playlist = BehaviorSubject.create();
    private TrackPlayer trackPlayer;
    private Subscription subscription;

    public EventPlayer(TrackPlayer trackPlayer) {
        this.trackPlayer = trackPlayer;
    }


    public void start() {
        subscription = trackPlayer.state().filter(playerState -> {
            Log.i(TAG, "onTrackPlayerStateChanged: state==" + playerState);
            return playerState.getPlayerState() == TrackPlayer.STATE_STOPPED;
        })
                .flatMap(state -> Observable.switchOnNext(playlist).first())
                .doOnUnsubscribe(trackPlayer::stop)
                .subscribe(track -> trackPlayer.play(track.getId()));
    }

    public void stop() {
        if(subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    public void playPause() {
        if(subscription != null) {
            trackPlayer.state().first().subscribe(playerState -> {
                int state = playerState.getPlayerState();
                if (state == TrackPlayer.STATE_PLAYING) {
                    trackPlayer.pause();
                } else if (state == TrackPlayer.STATE_PAUSED) {
                    trackPlayer.play();
                }
            });
        }
    }

    public void skip() {
        if(subscription != null) {
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
                    Log.i(TAG, "nextTrack: track.id==" + track.getId() + ", queue.size==" + trackQueue.size());
                    observer.onNext(track);
                } else {
                    Log.i(TAG, "nextTrack: no tracks left");
                    observer.onCompleted();
                }
                return trackQueue;
            }
        });
    }
}
