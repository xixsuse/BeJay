package rocks.itsnotrocketscience.bejay.api.retrofit;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by centralstation on 2/6/16.
 */
public interface GcmRegistration {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/gcm/v1/device/register/")
    Observable<Response> register(@Body GcmRegistrationDetails task);

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/gcm/v1/device/unregister/")
    Observable<Response> unregister(@Body String regId);
}
