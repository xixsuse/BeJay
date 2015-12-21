package rocks.itsnotrocketscience.bejay.api.retrofit;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Path;
import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by centralstation on 29/09/15.
 */
public interface CheckInUserToEvent {

    @POST("/events/{pk}/checkin_user/")
    void checkIn(@Path("pk") int pk, Callback<Event> response);

    @POST("/events/{pk}/checkout_user/")
    void checkOut(@Path("pk") int pk, Callback<Event> response);
}
