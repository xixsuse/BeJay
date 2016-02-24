package rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface Playlist {
    @GET("/playlist/{id}")
    Observable<rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Playlist> get(@Path("id") Long id);
}
