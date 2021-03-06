package rocks.itsnotrocketscience.bejay.api.retrofit;

import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import rocks.itsnotrocketscience.bejay.models.CmsUser;
import rx.Observable;

/**
 * Created by centralstation on 24/09/15.
 */
public interface CreateUser {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/users/")
    Observable<CmsUser> createUser(@Body CmsUser body);
}