package rocks.itsnotrocketscience.bejay.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by centralstation on 24/09/15.
 */
public class CmsUser {

    String email;
    @SerializedName("first_name")
    String firstName;
    @SerializedName("last_name")
    String lastName;

    //todo remove these
    @SerializedName("user_permissions")
    String userPermissions;
    String groups;

    String username;
    String password;

    public CmsUser(String userPermissions, String groups,String firstName, String lastName, String username, String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.userPermissions = userPermissions;
        this.groups=groups;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
