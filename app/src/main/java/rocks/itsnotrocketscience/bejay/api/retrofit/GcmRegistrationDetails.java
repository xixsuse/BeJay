package rocks.itsnotrocketscience.bejay.api.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by centralstation on 2/6/16.
 */
public class GcmRegistrationDetails {

    @SerializedName("device_id")
    private final String deviceId;
    @SerializedName("reg_id")
    private final String regId;
    private final String name;

    public GcmRegistrationDetails(String deviceId, String regId, String name) {
        this.deviceId = deviceId;
        this.regId = regId;
        this.name = name;
    }
}
