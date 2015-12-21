package rocks.itsnotrocketscience.bejay.api.retrofit;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import rocks.itsnotrocketscience.bejay.models.Song;
/**
 * Created by lduf0001 on 21/12/15.
 */
public interface PostSong {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/songs/")
    void postSong(@Body Song song, Callback<Song> response);
}
