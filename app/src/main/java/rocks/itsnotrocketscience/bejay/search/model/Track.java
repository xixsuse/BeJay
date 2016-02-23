package rocks.itsnotrocketscience.bejay.search.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nemi on 20/02/2016.
 *
 * Provider independent track interface declaration
 */
public class Track extends Model implements Parcelable {
    public static final String TYPE = "track";

    private String title;
    private String albumName;
    private String albumCover;
    private String artist;
    private Long duration;
    private String genre;
    private String cover;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String getType() {
        return TYPE;
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
            track.setGenre(source.readString());
            track.setCover(source.readString());
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
        dest.writeString(getId());
        dest.writeString(getProvider());
        dest.writeString(title);
        dest.writeString(albumName);
        dest.writeString(albumCover);
        if(duration == null) {
            dest.writeLong(-1);
        } else {
            dest.writeLong(duration);
        }
        dest.writeString(genre);
        dest.writeString(cover);
    }
}
