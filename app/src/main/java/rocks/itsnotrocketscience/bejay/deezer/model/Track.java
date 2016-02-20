package rocks.itsnotrocketscience.bejay.deezer.model;

import com.google.gson.annotations.SerializedName;

import rocks.itsnotrocketscience.bejay.deezer.Deezer;

import static rocks.itsnotrocketscience.bejay.deezer.Deezer.ALBUM;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.ARTIST;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.DURATION;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.EXPLICIT_LYRICS;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.ID;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.LINK;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PREVIEW;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.RANK;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.READABLE;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.TITLE;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.TITLE_SHORT;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.TITLE_VERSION;

/**
 * Created by nemi on 20/02/2016.
 */
public class Track {
    @SerializedName(ID) private Long id;
    @SerializedName(READABLE) private Boolean readable;
    @SerializedName(TITLE) private String title;
    @SerializedName(TITLE_SHORT) private String shortTitle;
    @SerializedName(TITLE_VERSION) private String titleVersion;
    @SerializedName(LINK) private String link;
    @SerializedName(DURATION) private Long duration;
    @SerializedName(RANK) private Long rank;
    @SerializedName(EXPLICIT_LYRICS) private boolean explicitLyrics;
    @SerializedName(PREVIEW) private String preview;
    @SerializedName(ARTIST) private Artist artist;
    @SerializedName(ALBUM) private Album album;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getReadable() {
        return readable;
    }

    public void setReadable(Boolean readable) {
        this.readable = readable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getTitleVersion() {
        return titleVersion;
    }

    public void setTitleVersion(String titleVersion) {
        this.titleVersion = titleVersion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public boolean isExplicitLyrics() {
        return explicitLyrics;
    }

    public void setExplicitLyrics(boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
