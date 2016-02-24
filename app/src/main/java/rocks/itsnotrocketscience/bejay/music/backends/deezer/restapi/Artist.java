package rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Album;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.CollectionResponse;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.PageResponse;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Track;
import rx.Observable;

public interface Artist {
    @GET("/artist/{id}/top")
    Observable<CollectionResponse<Track>> top(@Path("id") Long id);

    @GET("/artist/{id}/albums")
    Observable<PageResponse<Album>> albums(@Path("id") Long id, @Query("index") Long index);

    @GET("/artist/{id}")
    Observable<rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Artist> get(@Path("id") Long id);
}
