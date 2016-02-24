package rocks.itsnotrocketscience.bejay.music.backends.deezer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.ALTERNATIVE;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.ARTIST;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.AVAILABLE;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.CONTRIBUTORS;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.COVER;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.COVER_BIG;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.COVER_MEDIUM;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.COVER_SMALL;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.DURATION;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.EXPLICIT_LYRICS;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.FANS;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.GENRES;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.GENRE_ID;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.ID;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.LABEL;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.LINK;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.NUMBER_OF_TRACKS;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.RATING;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.RECORD_TYPE;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.RELEASE_DATE;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.SHARE;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.TITLE;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.TRACKS;
import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.UPC;

/**
 * Created by nemi on 20/02/2016.
 */
public class Album {
    @SerializedName(ID) private Long id;
    @SerializedName(TITLE) private String title;
    @SerializedName(UPC) private String upc;
    @SerializedName(LINK) private String link;
    @SerializedName(SHARE) private String share;
    @SerializedName(COVER) private String cover;
    @SerializedName(COVER_SMALL) private String coverSmall;
    @SerializedName(COVER_MEDIUM) private String coverMedium;
    @SerializedName(COVER_BIG) private String coverBig;
    @SerializedName(GENRE_ID) private Long genreId;
    @SerializedName(GENRES) private List<Genre> genres;
    @SerializedName(LABEL) private String label;
    @SerializedName(NUMBER_OF_TRACKS) private Long numberOfTracks;
    @SerializedName(DURATION) private Long duration;
    @SerializedName(FANS) private Long fans;
    @SerializedName(RATING) private Long rating;
    @SerializedName(RELEASE_DATE) private String releaseDate;
    @SerializedName(RECORD_TYPE) private String recordType;
    @SerializedName(AVAILABLE) private Boolean available;
    @SerializedName(ALTERNATIVE) private Album alternative;
    @SerializedName(EXPLICIT_LYRICS) private Boolean explicitLyrics;
    @SerializedName(CONTRIBUTORS) private List<String> contributors;
    @SerializedName(ARTIST) private Artist artist;
    @SerializedName(TRACKS) private List<Track> tracks;

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

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(Long numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getFans() {
        return fans;
    }

    public void setFans(Long fans) {
        this.fans = fans;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Album getAlternative() {
        return alternative;
    }

    public void setAlternative(Album alternative) {
        this.alternative = alternative;
    }

    public Boolean getExplicitLyrics() {
        return explicitLyrics;
    }

    public void setExplicitLyrics(Boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    public List<String> getContributors() {
        return contributors;
    }

    public void setContributors(List<String> contributors) {
        this.contributors = contributors;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
