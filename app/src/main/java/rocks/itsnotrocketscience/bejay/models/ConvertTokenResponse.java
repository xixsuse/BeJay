package rocks.itsnotrocketscience.bejay.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sirfunkenstine on 18/02/16.
 *
 */
public class ConvertTokenResponse {

    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("token_type")
    public String tokenType;
    @SerializedName("refresh_token")
    public  String refreshToken;
    @SerializedName("scope")
    public  String scope;
}
