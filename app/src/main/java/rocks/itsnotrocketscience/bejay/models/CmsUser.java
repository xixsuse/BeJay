package rocks.itsnotrocketscience.bejay.models;

/**
 * Created by centralstation on 24/09/15.
 */
public class CmsUser {

    String email;
    String first_name;
    String last_name;
    String username;
    String password;

    public CmsUser(String first_name, String last_name, String username, String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
