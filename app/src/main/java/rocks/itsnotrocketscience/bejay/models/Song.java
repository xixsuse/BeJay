package rocks.itsnotrocketscience.bejay.models;

/**
 * Created by centralstation on 20/08/15.
 */

public class Song {

    private String title;
    private Integer count;
    private Boolean chosen;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public Boolean getChosen() {
        return chosen;
    }

    public void setChosen(Boolean chosen) {
        this.chosen = chosen;
    }


}