package rocks.itsnotrocketscience.bejay.deezer.api;

import retrofit.http.GET;
import retrofit.http.Query;
import rocks.itsnotrocketscience.bejay.deezer.model.PageResponse;
import rocks.itsnotrocketscience.bejay.deezer.model.Track;
import rx.Observable;

/**
 * Created by nemi on 20/02/2016.
 */
public interface Search {
    @GET("/search/track")
    Observable<PageResponse<Track>> track(@Query("q")String query, @Query("index") Long index, @Query("limit") Long limit);
}
