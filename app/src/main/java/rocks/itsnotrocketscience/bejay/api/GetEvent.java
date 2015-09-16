package rocks.itsnotrocketscience.bejay.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by centralstation on 16/09/15.
 */
public interface GetEvent {
    @GET("/{url}/")
    public void getFeed(@Path("url") String url, Callback<Event> response);
}
