package rocks.itsnotrocketscience.bejay.tracks;

/**
 * Created by nemi on 20/02/2016.
 *
 * Provider independent track interface declaration
 */
public class Track {
    /**
     * Opaque identifier of track. Depends on provider
     * */
    private String id;

    /**
     * Descriptor of provider for this track
     * */
    private String provider;

    /**
     * Track title
     * */
    private String title;

    /**
     * Album name for this track
     * */
    private String albumName;

    /**
     * Link to album cover art
     * */
    private String albumCover;

    /**
     * Artist of track
     * */
    private String artist;

    /**
     * Duration of track
     * */
    private Long duration;

    /**
     * Genre of track
     * */
    private String genre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(String albumCover) {
        this.albumCover = albumCover;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
