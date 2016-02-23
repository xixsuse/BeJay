package rocks.itsnotrocketscience.bejay.deezer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import rocks.itsnotrocketscience.bejay.deezer.Deezer;

import static rocks.itsnotrocketscience.bejay.deezer.Deezer.ID;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.LINK;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.NUMBER_OF_TRACKS;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PICTURE;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PICTURE_BIG;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PICTURE_MEDIUM;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PICTURE_SMALL;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PUBLIC;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.TITLE;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.USER;

public class Playlist {
    @SerializedName(ID) private Long id;
    @SerializedName(TITLE) private String title;
    @SerializedName(PUBLIC) private Boolean pub;
    @SerializedName(NUMBER_OF_TRACKS) private Long numberOfTracks;
    @SerializedName(LINK) private String link;
    @SerializedName(PICTURE) private String picture;
    @SerializedName(PICTURE_SMALL) private String smallPicture;
    @SerializedName(PICTURE_MEDIUM) private String mediumPicture;
    @SerializedName(PICTURE_BIG) private String bigPicture;
    @SerializedName(USER) private User user;
    @SerializedName(Deezer.TRACKS) private List<Track> tracks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isPublic() {
        return pub;
    }

    public void setPublic(Boolean pub) {
        this.pub = pub;
    }

    public Long getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(Long numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSmallPicture() {
        return smallPicture;
    }

    public void setSmallPicture(String smallPicture) {
        this.smallPicture = smallPicture;
    }

    public String getMediumPicture() {
        return mediumPicture;
    }

    public void setMediumPicture(String mediumPicture) {
        this.mediumPicture = mediumPicture;
    }

    public String getBigPicture() {
        return bigPicture;
    }

    public void setBigPicture(String bigPicture) {
        this.bigPicture = bigPicture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getPub() {
        return pub;
    }

    public void setPub(Boolean pub) {
        this.pub = pub;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
