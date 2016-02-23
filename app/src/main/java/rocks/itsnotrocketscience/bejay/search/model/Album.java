package rocks.itsnotrocketscience.bejay.search.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {
    private String provider;
    private String id;
    private String title;
    private String cover;
    private String artist;


    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(provider);
        dest.writeString(id);
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
