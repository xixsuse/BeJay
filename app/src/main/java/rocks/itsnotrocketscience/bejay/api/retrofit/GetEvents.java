package rocks.itsnotrocketscience.bejay.api.retrofit;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by centralstation on 20/08/15.
 */
public interface GetEvents {
    @GET("/{url}/")
    void getFeed(@Path("url") String url, Callback<ArrayList<Event>> response);
}
