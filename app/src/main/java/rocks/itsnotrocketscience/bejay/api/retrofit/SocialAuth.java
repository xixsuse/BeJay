package rocks.itsnotrocketscience.bejay.api.retrofit;

import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;
import rocks.itsnotrocketscience.bejay.models.ConvertTokenResponse;
import rx.Observable;

/**
 * Created by sirfunkenstine on 18/02/16.
 */
public interface SocialAuth {

    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Accept: */*",
            "cache-control: no-cache",
    })
    @POST("/auth/convert-token")
    Observable<ConvertTokenResponse> convertToken(@Query("grant_type") String grant_type,
                                                  @Query("client_id") String client_id,
                                                  @Query("client_secret") String client_secret,
                                                  @Query("backend") String backend,
                                                  @Query("token") String token
    );
}
