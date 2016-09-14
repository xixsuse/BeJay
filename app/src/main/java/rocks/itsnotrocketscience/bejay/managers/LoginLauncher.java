package rocks.itsnotrocketscience.bejay.managers;

import android.content.Intent;

import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.main.MainActivity;

public class LoginLauncher extends LauncherBase {
    private final LoginActivity loginActivity;

    public LoginLauncher(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void search() {
        Intent intent = new Intent(loginActivity, MainActivity.class);
        loginActivity.startActivity(intent);
        loginActivity.finish();
    }
}
