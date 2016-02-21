package rocks.itsnotrocketscience.bejay.tracks;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nemi on 20/02/2016.
 *
 * Provider independent track interface declaration
 */
public class Track implements Parcelable {
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

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel source) {
            Track track = new Track();
            track.setId(source.readString());
            track.setProvider(source.readString());
            track.setTitle(source.readString());
            track.setAlbumName(source.readString());
            track.setAlbumCover(source.readString());
            long duration = source.readLong();
            if(duration >= -1) {
                track.setDuration(duration);
            }
            return track;
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(provider);
        dest.writeString(title);
        dest.writeString(albumName);
        dest.writeString(albumCover);
        if(duration == null) {
            dest.writeLong(-1);
        } else {
            dest.writeLong(duration);
        }
        dest.writeString(genre);
    }
}
