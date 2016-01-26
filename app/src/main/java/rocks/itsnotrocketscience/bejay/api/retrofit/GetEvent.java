package rocks.itsnotrocketscience.bejay.api.retrofit;


import retrofit.http.GET;
import retrofit.http.Path;
import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;

/**
 * Created by centralstation on 16/09/15.
 */
public interface GetEvent {
    @GET("/events/{url}/")
    Observable<Event> getFeed(@Path("url") int url);
}
