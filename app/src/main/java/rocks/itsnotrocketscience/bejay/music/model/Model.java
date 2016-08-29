package rocks.itsnotrocketscience.bejay.music.model;

import android.support.annotation.IntDef;

public abstract class Model {
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_ALBUM = 1;
    public static final int TYPE_ARTIST = 2;
    public static final int TYPE_PLAYLIST = 3;
    public static final int TYPE_TRACK = 4;
    public static final int TYPE_FIRST = 5;

    @IntDef(value = {TYPE_UNKNOWN, TYPE_ALBUM, TYPE_ARTIST, TYPE_PLAYLIST, TYPE_TRACK})
    public @interface Type {}

    private String provider;
    private String id;

    String getProvider() {
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

    public abstract @Type int getType();


}
