package rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi;

import retrofit.http.GET;
import retrofit.http.Query;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Album;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Artist;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.PageResponse;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Playlist;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Track;
import rx.Observable;

/**
 * Created by nemi on 20/02/2016.
 */
public interface Search {

    @GET("/search/track")
    Observable<PageResponse<Track>> track(@Query("q")String query, @Query("index") Long index, @Query("limit") Long limit);

    @GET("/search/artist")
    Observable<PageResponse<Artist>> artist(@Query("q")String query, @Query("index") Long index, @Query("limit") Long limit);

    @GET("/search/album")
    Observable<PageResponse<Album>> album(@Query("q")String query, @Query("index") Long index, @Query("limit") Long limit);

    @GET("/search/playlist")
    Observable<PageResponse<Playlist>> playlist(@Query("q")String query, @Query("index") Long index, @Query("limit") Long limit);
}
