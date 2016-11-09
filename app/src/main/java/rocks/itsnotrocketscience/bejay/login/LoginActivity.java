package rocks.itsnotrocketscience.bejay.login;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.dagger.DaggerLoginComponent;
import rocks.itsnotrocketscience.bejay.dagger.LoginComponent;
import rocks.itsnotrocketscience.bejay.dagger.LoginModule;

public class LoginActivity extends InjectedActivity<LoginComponent> {
    private final LoginModule loginModule;
    private LoginComponent loginComponent;

    public LoginActivity() {
        this.loginModule = new LoginModule(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginComponent = DaggerLoginComponent.builder()
                .appComponent(getAppComponent())
                .loginModule(loginModule)
                .build();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, LoginFragment.newInstance())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public LoginComponent getComponent() {
        return loginComponent;
    }

}
