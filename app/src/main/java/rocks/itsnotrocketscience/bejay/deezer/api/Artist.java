package rocks.itsnotrocketscience.bejay.deezer.api;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rocks.itsnotrocketscience.bejay.deezer.model.Album;
import rocks.itsnotrocketscience.bejay.deezer.model.CollectionResponse;
import rocks.itsnotrocketscience.bejay.deezer.model.PageResponse;
import rocks.itsnotrocketscience.bejay.deezer.model.Track;
import rx.Observable;

public interface Artist {
    @GET("/artist/{id}/top")
    Observable<CollectionResponse<Track>> top(@Path("id") Long id);

    @GET("/artist/{id}/albums")
    Observable<PageResponse<Album>> albums(@Path("id") Long id, @Query("index") Long index);
}
