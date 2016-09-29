package rocks.itsnotrocketscience.bejay.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by centralstation on 20/08/15.
 */
public class Event implements Visitable {

    private int id;
    @SerializedName("songs")
    private List<Song> songs = new ArrayList<>();
    @SerializedName("order")
    private Integer order;
    @SerializedName("publish")
    private Boolean publish;
    @SerializedName("is_public")
    private Boolean isPublic;
    @SerializedName("creator")
    private String creator;
    @SerializedName("uid")
    private String uid;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;
    @SerializedName("title")
    private String title;
    @SerializedName("appUser")
    private String appUser;
    @SerializedName("details")
    private String details;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("place")
    private String place;
    @SerializedName("geo_cords")
    private String geo_cords;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDetails(String details) {
        this.details = details;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setGps(LatLng gps) {
        geo_cords = String.format("SRID=4326;POINT (%s %s)", gps.latitude, gps.latitude);
    }

    public boolean hasGps() {
        return geo_cords!=null;
    }

    public boolean hasStartTime() {
        return startDate != null;
    }

    public boolean hasEndDate() {
        return endDate != null;
    }

    @Override public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}