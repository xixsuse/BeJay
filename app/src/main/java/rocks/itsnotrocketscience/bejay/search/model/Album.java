package rocks.itsnotrocketscience.bejay.search.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Album extends Model implements Parcelable {
    private String title;
    private String cover;
    private String artist;


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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
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
        dest.writeString(artist);
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            Album album = new Album();
            album.setProvider(source.readString());
            album.setId(source.readString());
            album.setTitle(source.readString());
            album.setCover(source.readString());
            album.setArtist(source.readString());
            return album;
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
