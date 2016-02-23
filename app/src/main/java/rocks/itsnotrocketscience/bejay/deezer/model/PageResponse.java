package rocks.itsnotrocketscience.bejay.deezer.model;


import com.google.gson.annotations.SerializedName;

import static rocks.itsnotrocketscience.bejay.deezer.Deezer.DATA;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.NEXT;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PREV;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.TOTAL;

/**
 * Created by nemi on 20/02/2016.
 */
public class PageResponse<T> extends CollectionResponse<T> {

    @SerializedName(NEXT) private String next;
    @SerializedName(PREV) private String prev;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }
}
