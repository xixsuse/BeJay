package rocks.itsnotrocketscience.bejay.api.retrofit;

/**
 * Created by centralstation on 28/09/15.
 */
public class AuthCredentials {
    private String username;
    private String password;

    public AuthCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
