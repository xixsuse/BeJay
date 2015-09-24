package rocks.itsnotrocketscience.bejay.api;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rocks.itsnotrocketscience.bejay.models.CmsUser;

/**
 * Created by centralstation on 24/09/15.
 */
public interface CreateUser {
    @FormUrlEncoded
    @POST("/dukebox/user")
    void getUserLogin(@Field("password") String password, @Field("email") String email, Callback<CmsUser> cb);
}