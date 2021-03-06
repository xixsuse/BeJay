package rocks.itsnotrocketscience.bejay.models;

/**
 * Created by centralstation on 20/08/15.
 */

public class Song {

    private String title;
    private int id;
    private int liked;
    private int likes;
    private Boolean chosen;

    public Song(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountStr() {
        return String.valueOf(likes);
    }

    public int getId() {
        return id;
    }

    public boolean  hasLikeId() {
        return liked!=-1;
    }

    public void updateLikes(int update) {
        likes += update;
    }

    public int getLikeId(){
        return liked;
    }

    public void updateLiked(int id) {
        liked=id;
    }
}