package rocks.itsnotrocketscience.bejay.api.retrofit;

/**
 * Created by centralstation on 28/09/15.
 */
class AuthCredentials {
    private final String username;
    private final String password;

    public AuthCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
