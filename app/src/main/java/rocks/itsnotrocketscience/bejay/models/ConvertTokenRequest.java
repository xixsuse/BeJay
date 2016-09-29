package rocks.itsnotrocketscience.bejay.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sirfunkenstine on 18/02/16.
 * request object for registering user with server through social auth.
 *
 */
class ConvertTokenRequest {

    @SerializedName("grant_type")
    private final
    String grantType;
    @SerializedName("client_id")
    private final
    String clientId;
    @SerializedName("client_secret")
    private final
    String clientSecret;
    private final String backend;
    private final String token;

    public ConvertTokenRequest(String grantType, String clientId, String clientSecret, String backend, String token) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.backend = backend;
        this.token = token;
    }
}