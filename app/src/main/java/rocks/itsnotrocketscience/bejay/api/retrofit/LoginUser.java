package rocks.itsnotrocketscience.bejay.api.retrofit;


import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import rocks.itsnotrocketscience.bejay.models.Token;
import rx.Observable;

/**
 * Created by centralstation on 28/09/15.
 */
public interface LoginUser {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/{url}/")
    Observable<Token> loginUser(@Path("url") String url, @Body AuthCredentials task);
}

