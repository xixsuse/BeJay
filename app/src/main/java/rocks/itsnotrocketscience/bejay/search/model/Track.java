package rocks.itsnotrocketscience.bejay.search.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by nemi on 20/02/2016.
 *
 * Provider independent track interface declaration
 */
public class Track extends Model implements Parcelable {
    private String title;
    private Long duration;
    private String genre;
    private String cover;
    private Artist artist;
    private Album album;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public int getType() {
        return TYPE_TRACK;
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel source) {
            Track track = new Track();
            track.setId(source.readString());
            track.setProvider(source.readString());
            track.setTitle(source.readString());

            if(source.readInt() == 1) {
                Artist artist = source.readParcelable(Artist.class.getClassLoader());
                track.setArtist(artist);
            }

            long duration = source.readLong();
            if(duration >= -1) {
                track.setDuration(duration);
            }

            track.setGenre(source.readString());
            track.setCover(source.readString());

            if(source.readInt() == 1) {
                Album album = source.readParcelable(Album.class.getClassLoader());
                track.setAlbum(album);
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
        dest.writeString(getId());
        dest.writeString(getProvider());
        dest.writeString(title);

        if(artist != null) {
            dest.writeInt(1);
            dest.writeParcelable(artist, 0);
        } else {
            dest.writeInt(0);
        }

        if(duration == null) {
            dest.writeLong(-1);
        } else {
            dest.writeLong(duration);
        }

        dest.writeString(genre);
        dest.writeString(cover);

        if(album != null) {
            dest.writeInt(1);
            dest.writeParcelable(album, 0);
        } else {
            dest.writeInt(0);
        }
    }
}
