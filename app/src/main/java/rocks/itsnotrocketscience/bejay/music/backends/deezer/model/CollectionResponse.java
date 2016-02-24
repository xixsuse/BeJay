package rocks.itsnotrocketscience.bejay.music.backends.deezer.model;

import com.google.gson.annotations.SerializedName;

import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.DATA;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.TOTAL;

public class CollectionResponse<T> extends Collection<T> {
    @SerializedName(TOTAL) private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
