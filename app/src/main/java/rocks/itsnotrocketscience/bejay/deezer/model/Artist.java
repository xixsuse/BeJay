package rocks.itsnotrocketscience.bejay.deezer.model;

import com.google.gson.annotations.SerializedName;

import rocks.itsnotrocketscience.bejay.deezer.Deezer;

import static rocks.itsnotrocketscience.bejay.deezer.Deezer.HAS_SMART_RADIO;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.ID;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.LINK;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.NAME;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.NUMBER_OF_ALBUMS;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.NUMBER_OF_FANS;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PICTURE;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PICTURE_BIG;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PICTURE_MEDIUM;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PICTURE_SMALL;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.SHARE;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.TRACK_LIST;

/**
 * Created by nemi on 20/02/2016.
 */
public class Artist {
    @SerializedName(ID) private Long id;
    @SerializedName(NAME) private String name;
    @SerializedName(LINK) private String link;
    @SerializedName(SHARE) private String share;
    @SerializedName(PICTURE) private String picture;
    @SerializedName(PICTURE_SMALL) private String pictureSmall;
    @SerializedName(PICTURE_MEDIUM) private String pictureMedium;
    @SerializedName(PICTURE_BIG) private String pictureBig;
    @SerializedName(NUMBER_OF_ALBUMS) private Long numberOfAlbums;
    @SerializedName(NUMBER_OF_FANS) private Long numberOfFans;
    @SerializedName(HAS_SMART_RADIO) private Boolean hasSmartRadio;
    @SerializedName(TRACK_LIST) private String trackList;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
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

    public Long getNumberOfAlbums() {
        return numberOfAlbums;
    }

    public void setNumberOfAlbums(Long numberOfAlbums) {
        this.numberOfAlbums = numberOfAlbums;
    }

    public Long getNumberOfFans() {
        return numberOfFans;
    }

    public void setNumberOfFans(Long numberOfFans) {
        this.numberOfFans = numberOfFans;
    }

    public Boolean getHasSmartRadio() {
        return hasSmartRadio;
    }

    public void setHasSmartRadio(Boolean hasSmartRadio) {
        this.hasSmartRadio = hasSmartRadio;
    }

    public String getTrackList() {
        return trackList;
    }

    public void setTrackList(String trackList) {
        this.trackList = trackList;
    }
}
