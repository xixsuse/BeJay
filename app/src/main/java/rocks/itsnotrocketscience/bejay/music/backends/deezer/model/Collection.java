package rocks.itsnotrocketscience.bejay.music.backends.deezer.model;

import com.google.gson.annotations.SerializedName;

import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.DATA;

public class Collection<T> {
    @SerializedName(DATA) private java.util.List<T> data;


    public java.util.List<T> getData() {
        return data;
    }

    public void setData(java.util.List<T> data) {
        this.data = data;
    }


}
