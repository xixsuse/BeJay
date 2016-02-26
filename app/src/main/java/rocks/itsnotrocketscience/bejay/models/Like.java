package rocks.itsnotrocketscience.bejay.models;

/**
 * Created by sirfunkenstine on 23/02/16.
 */
public class Like {
    int song;

    public Like(Song song) {
        this.song = song.getId();
    }
}
