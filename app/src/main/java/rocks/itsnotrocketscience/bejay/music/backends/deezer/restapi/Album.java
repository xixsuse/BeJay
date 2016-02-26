package rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface Album {
    @GET("/album/{id}")
    Observable<rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Album> get(@Path("id") Long id);
}
