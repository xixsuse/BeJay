package rocks.itsnotrocketscience.bejay.api.retrofit;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by centralstation on 16/09/15.
 *
 */
public interface GetEvent {
    @GET("/events/{url}/")
    void getFeed(@Path("url") int url, Callback<Event> response);
}
