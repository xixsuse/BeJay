package rocks.itsnotrocketscience.bejay.deezer;

import java.util.List;

import rocks.itsnotrocketscience.bejay.tracks.Track;
import rocks.itsnotrocketscience.bejay.tracks.TrackSearch;
import rx.Observable;

/**
 * Created by nemi on 20/02/2016.
 */
public class DeezerTrackSearch implements TrackSearch {

    @Override
    public Observable<List<Track>> loadMoreResults() {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
