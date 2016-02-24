package rocks.itsnotrocketscience.bejay.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sirfunkenstine on 18/02/16.
 * request object for registering user with server through social auth.
 *
 */
public class ConvertTokenRequest {

    @SerializedName("grant_type")
    String grantType;
    @SerializedName("client_id")
    String clientId;
    @SerializedName("client_secret")
    String clientSecret;
    String backend;
    String token;

    public ConvertTokenRequest(String grantType, String clientId, String clientSecret, String backend, String token) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.backend = backend;
        this.token = token;
    }
}