package rocks.itsnotrocketscience.bejay.music.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist extends Model implements Parcelable {
    private String name;
    private String picture;


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
    public int getType() {
        return TYPE_ARTIST;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getProvider());
        dest.writeString(getId());
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
