package rocks.itsnotrocketscience.bejay.music.backends.deezer.model;

import com.google.gson.annotations.SerializedName;

import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.DATA;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.TOTAL;

public class CollectionResponse<T> {
    @SerializedName(DATA) private java.util.List<T> data;
    @SerializedName(TOTAL) private Long total;


    public java.util.List<T> getData() {
        return data;
    }

    public void setData(java.util.List<T> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
