package rocks.itsnotrocketscience.bejay.models;

/**
 * Created by sirfunkenstine on 23/02/16.
 */
public class Like {
    int song;
    int id;

    public Like(Song song) {
        this.song = song.getId();
    }

    public int getId() {
        return id;
    }
}
