package rocks.itsnotrocketscience.bejay.deezer.model;


import com.google.gson.annotations.SerializedName;

import rocks.itsnotrocketscience.bejay.deezer.Deezer;

/**
 * Created by nemi on 20/02/2016.
 */
public class Collection<T> {
    @SerializedName(Deezer.DATA) private java.util.List<T> data;

    public java.util.List<T> getData() {
        return data;
    }

    public void setData(java.util.List<T> data) {
        this.data = data;
    }
}
