package rocks.itsnotrocketscience.bejay.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.login.LoginContract;
import rocks.itsnotrocketscience.bejay.login.LoginPresenterImpl;
import rocks.itsnotrocketscience.bejay.managers.Launcher;
import rocks.itsnotrocketscience.bejay.managers.LoginLauncher;

@Module
public class LoginModule {
    private final LoginActivity loginActivity;

    public LoginModule(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Provides @PerActivity Launcher providesLauncher() {
        return new LoginLauncher(loginActivity);
    }

    @Provides LoginContract.LoginPresenter providesLoginPresenter(Context context, SharedPreferences sharedPreferences) {
        return new LoginPresenterImpl(context, sharedPreferences);
    }
}
