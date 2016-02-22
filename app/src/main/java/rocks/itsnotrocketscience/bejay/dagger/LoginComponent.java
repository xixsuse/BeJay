package rocks.itsnotrocketscience.bejay.dagger;

import dagger.Component;
import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.login.LoginFragment;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

@PerActivity
@Component(modules = LoginModule.class, dependencies = {AppComponent.class})
public interface LoginComponent {
    Launcher launcher();

    void inject(LoginActivity loginActivity);
    void inject(LoginFragment loginFragment);
}
