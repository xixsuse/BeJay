package rocks.itsnotrocketscience.bejay.tracks.search;

import java.util.List;

import rocks.itsnotrocketscience.bejay.tracks.Track;
import rx.Observable;

/**
 * Created by nemi on 20/02/2016.
 *
 * Track search interface for paginate track search result loading
 */
public interface TrackSearch {
    /**
     * Load more page search results
     * */
    Observable<List<Track>> loadMoreResults();
}
