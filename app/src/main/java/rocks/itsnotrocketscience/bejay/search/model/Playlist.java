package rocks.itsnotrocketscience.bejay.search.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Playlist extends Model implements Parcelable {
    private String title;
    private Boolean pub;
    private Long numberOfTracks;
    private String picture;
    private String user;

    @Override
    public int getType() {
        return Model.TYPE_PLAYLIST;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isPublic() {
        return pub;
    }

    public void setPublic(Boolean pub) {
        this.pub = pub;
    }

    public Long getNumberOfTracks() {
        return numberOfTracks;
    }

    public void setNumberOfTracks(Long numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
        if(pub == null) {
            dest.writeInt(-1);
        } else {
            dest.writeInt(pub ? 1 : 0);
        }

        if(numberOfTracks == null) {
            dest.writeLong(-1l);
        } else {
            dest.writeLong(numberOfTracks);
        }
        dest.writeString(picture);
        dest.writeString(user);
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel source) {
            Playlist playlist = new Playlist();
            playlist.setProvider(source.readString());
            playlist.setId(source.readString());
            playlist.setTitle(source.readString());
            int pub = source.readInt();
            if(pub > 0) {
                playlist.setPublic(pub == 1);
            }
            return playlist;
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };
}
