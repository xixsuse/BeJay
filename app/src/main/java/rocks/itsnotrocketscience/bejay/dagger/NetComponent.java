package rocks.itsnotrocketscience.bejay.dagger;

import javax.inject.Singleton;

import dagger.Component;
import rocks.itsnotrocketscience.bejay.login.LoginFragment;

/**
 * Created by lduf0001 on 21/12/15.
 */

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {

   void inject(LoginFragment loginFragment);

}
