package rocks.itsnotrocketscience.bejay.deezer;

import rocks.itsnotrocketscience.bejay.deezer.api.Search;
import rocks.itsnotrocketscience.bejay.tracks.search.TrackSearch;
import rocks.itsnotrocketscience.bejay.tracks.search.TrackSearchManager;

/**
 * Created by nemi on 20/02/2016.
 */
public class DeezerTrackSearchManager implements TrackSearchManager {
    private final Search search;

    public DeezerTrackSearchManager(Search search) {
        this.search = search;
    }

    @Override
    public TrackSearch newSearch(String query, int limit) {
        return new DeezerTrackSearch(search, query, limit);
    }
}
