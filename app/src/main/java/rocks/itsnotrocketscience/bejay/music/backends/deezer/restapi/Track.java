package rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface Track {
    @GET("/track/{id}")
    Observable<rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Track> get(@Path("id") Long id);
}
