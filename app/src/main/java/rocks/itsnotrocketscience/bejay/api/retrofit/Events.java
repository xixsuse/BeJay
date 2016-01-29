package rocks.itsnotrocketscience.bejay.api.retrofit;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;

/**
 * Created by nemi on 27/01/2016.
 */
public interface Events {
    @GET("/events")
    Observable<ArrayList<Event>> list();

    @GET("/events/{id}")
    Observable<Event> list(@Path("id") int id);

    @POST("/events/{id}/checkin_user/")
    Observable<Event> checkIn(@Path("id") int id);

    @POST("/events/{id}/checkout_user/")
    Observable<Event> checkOut(@Path("id") int id);
}
