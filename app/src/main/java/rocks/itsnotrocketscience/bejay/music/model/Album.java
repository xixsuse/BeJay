package rocks.itsnotrocketscience.bejay.music.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Album extends Model implements Parcelable {
    private String title;
    private String cover;
    private Artist artist;
    private List<Track> tracks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public int getType() {
        return TYPE_ALBUM;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getProvider());
        dest.writeString(getId());
        dest.writeString(title);
        dest.writeString(cover);

        if(artist != null) {
            dest.writeInt(1);
            dest.writeParcelable(artist, 0);
        } else {
            dest.writeInt(0);
        }

        if(tracks != null) {
            dest.writeInt(1);
            Track[] tracks = this.tracks.toArray(new Track[this.tracks.size()]);
            dest.writeParcelableArray(tracks, 0);
        } else {
            dest.writeInt(0);
        }
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            Album album = new Album();
            album.setProvider(source.readString());
            album.setId(source.readString());
            album.setTitle(source.readString());
            album.setCover(source.readString());
            if(source.readInt() == 1) {
                Artist artist = source.readParcelable(Artist.class.getClassLoader());
                album.setArtist(artist);
            }

            if(source.readInt() == 1) {
                Parcelable[] tracks = source.readParcelableArray(Track.class.getClassLoader());
                album.setTracks(new ArrayList<>(tracks.length));
                for(Parcelable track : tracks) {
                    album.getTracks().add((Track) track);
                }
            }


            return album;
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
