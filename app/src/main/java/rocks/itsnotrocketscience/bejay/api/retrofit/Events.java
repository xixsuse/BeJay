package rocks.itsnotrocketscience.bejay.api.retrofit;

import java.util.ArrayList;
import java.util.Map;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;
import rx.Observable;

/**
 * Created by nemi on 27/01/2016.
 */
public interface Events {
    @GET("/events")
    Observable<ArrayList<Event>> list();

    @GET("/events/{id}")
    Observable<Event> get(@Path("id") int id);

    @POST("/events/{id}/checkin_user/")
    Observable<Event> checkIn(@Path("id") int id);

    @POST("/events/{id}/checkout_user/")
    Observable<Event> checkOut(@Path("id") int id);

    @POST("/events/{song_id}/play/")
    Observable<Map<String,String>> play(@Path("song_id") int id);

    @POST("/events/pause/")
    Observable<Map<String,String>> pause();

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/songs/")
    Observable<Song> postSong(@Body Song song);
}
