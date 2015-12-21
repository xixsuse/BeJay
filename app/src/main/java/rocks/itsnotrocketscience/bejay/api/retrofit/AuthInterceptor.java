package rocks.itsnotrocketscience.bejay.api.retrofit;

import retrofit.RequestInterceptor;

/**
 * Created by centralstation on 28/09/15.
 */
public class AuthInterceptor implements RequestInterceptor {
    String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public void intercept(RequestFacade request) {

        if (token != null) {
            request.addHeader("Authorization", "Token " + token);
        }
    }
}
