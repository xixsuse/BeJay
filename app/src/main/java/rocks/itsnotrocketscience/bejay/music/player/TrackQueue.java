package rocks.itsnotrocketscience.bejay.music.player;

import java.util.List;

import rocks.itsnotrocketscience.bejay.music.model.Track;
import rx.Observable;
import rx.Observer;
import rx.observables.SyncOnSubscribe;

/**
 * Created by nemi on 26/04/16.
 */
class TrackQueue extends SyncOnSubscribe<List<Track>, Track> {
    private final List<Track> trackList;

    static Observable<Track> trackQueue(List<Track> trackList) {
        return Observable.create(new TrackQueue(trackList));
    }

    TrackQueue(List<Track> trackList) {
        this.trackList = trackList;
    }

    @Override
    protected List<Track> generateState() {
        return trackList;
    }

    @Override
    protected List<Track> next(List<Track> trackList, Observer<? super Track> observer) {
        if(trackList.size() > 0) {
            Track track = trackList.remove(0);
            observer.onNext(track);
        } else {
            observer.onCompleted();
        }

        return trackList;
    }
}
