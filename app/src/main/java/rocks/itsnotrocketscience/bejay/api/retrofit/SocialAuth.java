package rocks.itsnotrocketscience.bejay.api.retrofit;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import rocks.itsnotrocketscience.bejay.models.ConvertTokenResponse;
import rx.Observable;

/**
 * Created by sirfunkenstine on 18/02/16.
 */
public interface SocialAuth {

    @FormUrlEncoded
    @POST("/auth/convert-token")
    Observable<ConvertTokenResponse> convertToken(@Field("grant_type") String grant_type,
                                                  @Field("client_id") String client_id,
                                                  @Field("client_secret") String client_secret,
                                                  @Field("backend") String backend,
                                                  @Field("token") String token
    );
}
