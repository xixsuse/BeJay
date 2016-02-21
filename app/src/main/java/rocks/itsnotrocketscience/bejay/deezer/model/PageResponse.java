package rocks.itsnotrocketscience.bejay.deezer.model;


import com.google.gson.annotations.SerializedName;

import rocks.itsnotrocketscience.bejay.deezer.Deezer;

import static rocks.itsnotrocketscience.bejay.deezer.Deezer.DATA;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.TOTAL;

/**
 * Created by nemi on 20/02/2016.
 */
public class PageResponse<T> {
    @SerializedName(DATA) private java.util.List<T> data;
    @SerializedName(TOTAL) private Long total;
    @SerializedName(Deezer.NEXT) private String next;

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

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
