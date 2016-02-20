package rocks.itsnotrocketscience.bejay.api.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by centralstation on 2/6/16.
 */
public class GcmRegistrationDetails {

    @SerializedName("dev_id")
    private final String devId;
    @SerializedName("reg_id")
    private final String regId;
    private final String name;

    public GcmRegistrationDetails(String devId, String regId, String name) {
        this.devId = devId;
        this.regId = regId;
        this.name = name;
    }
}
