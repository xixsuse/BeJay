package rocks.itsnotrocketscience.bejay.tracks.search;

/**
 * Created by nemi on 20/02/2016.
 *
 * The track search manager is responsible for creating a new TrackSearch object
 * based on query string and limit( a.k.a. page size)
 */
public interface TrackSearchManager {
    /**
     * Return new TrackSearch based on query and limit
     *
     * @param query search query string
     * @param limit result page size
     * @return track search object
     * */
    TrackSearch newSearch(String query, int limit);

}
