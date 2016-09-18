package rocks.itsnotrocketscience.bejay.api.retrofit;

import java.util.ArrayList;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Like;
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

    @GET("/events/public_nearby_events/")
    Observable<ArrayList<Event>> publicNearbyEvents(@Query("lat") long lat, @Query("lng") long lng);

    @GET("/events/search_events/")
    Observable<ArrayList<Event>> searchEvents(@Query("search_term") String term);

    @GET("/events/friends_events/")
    Observable<ArrayList<Event>> friendsEvents();

    @POST("/events/{id}/checkin_user/")
    Observable<Event> checkIn(@Path("id") int id);

    @POST("/events/{id}/checkout_user/")
    Observable<Event> checkOut(@Path("id") int id);

    @POST("/events/{id}/play/")
    Observable<Map<String,String>> play(@Path("id") int id, @Query("song_id") int song_id);

    @GET("/events/{id}/pause/")
    Observable<Map<String,String>> pause(@Path("id") int id);

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/songs/")
    Observable<Song> postSong(@Body Song song);

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/likes/")
    Observable<Like> postLike(@Body Like song);

    @DELETE("/likes/{id}/")
    Observable<Like> deleteLike(@Path("id") int id);

    @POST("/events/")
    Observable<Event> postEvent(@Body Event event);


}
