package rocks.itsnotrocketscience.bejay.music.backends.deezer.model;

import com.google.gson.annotations.SerializedName;

import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.ID;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.NAME;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.PICTURE;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.PICTURE_BIG;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.PICTURE_MEDIUM;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.PICTURE_SMALL;

/**
 * Created by nemi on 20/02/2016.
 */
class Genre {
    @SerializedName(ID) private Long id;
    @SerializedName(NAME) private String name;
    @SerializedName(PICTURE) private String picture;
    @SerializedName(PICTURE_SMALL) private String pictureSmall;
    @SerializedName(PICTURE_MEDIUM) private String pictureMedium;
    @SerializedName(PICTURE_BIG) private String pictureBig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureSmall() {
        return pictureSmall;
    }

    public void setPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
    }

    public String getPictureMedium() {
        return pictureMedium;
    }

    public void setPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
    }

    public String getPictureBig() {
        return pictureBig;
    }

    public void setPictureBig(String pictureBig) {
        this.pictureBig = pictureBig;
    }
}
