package rocks.itsnotrocketscience.bejay.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by centralstation on 20/08/15.
 *
 */
public class Event{

    private int id;
    private List<Song> songs = new ArrayList<>();
    private Integer order;
    private Boolean publish;
    private String uid;
    private String created;
    private String modified;
    private String title;
    private String appUser;

    public int getId() {
        return id;
    }


    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser;
    }

}