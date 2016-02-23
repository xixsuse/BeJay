package rocks.itsnotrocketscience.bejay.search;

/**
 * Created by nemi on 20/02/2016.
 *
 * The track search manager is responsible for creating a new TrackSearch object
 * based on query string and limit( a.k.a. page size)
 */
public interface SearchProvider<R> {
    /**
     * Return new TrackSearch based on query and limit
     *
     * @param query search query string
     * @param limit result page size
     * @return track search object
     * */
    Search<R> newSearch(String query, long limit);

}
