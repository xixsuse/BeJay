package rocks.itsnotrocketscience.bejay.search.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {
    private String provider;
    private String id;
    private String name;
    private String picture;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(provider);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(picture);

    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            Artist artist = new Artist();
            artist.setProvider(source.readString());
            artist.setId(source.readString());
            artist.setName(source.readString());
            artist.setPicture(source.readString());
            return artist;
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
