package rocks.itsnotrocketscience.bejay.music.backends.deezer.model;


import com.google.gson.annotations.SerializedName;

import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.NEXT;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.PREV;

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
