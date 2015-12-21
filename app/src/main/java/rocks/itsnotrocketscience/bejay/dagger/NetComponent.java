package rocks.itsnotrocketscience.bejay.dagger;

import javax.inject.Singleton;

import dagger.Component;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.event.list.EventListFragment;
import rocks.itsnotrocketscience.bejay.event.single.EventActivity;
import rocks.itsnotrocketscience.bejay.gcm.RegistrationIntentService;
import rocks.itsnotrocketscience.bejay.login.LoginFragment;
import rocks.itsnotrocketscience.bejay.main.NavigationDrawerFragment;

/**
 * Created by lduf0001 on 21/12/15.
 */

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {

   void inject(LoginFragment loginFragment);
   void inject(EventListFragment eventListFragment);
   void inject(EventActivity eventActivity);
   void inject(NavigationDrawerFragment navigationDrawerFragment);
   void inject(BaseFragment baseFragment);
   void inject(BaseActivity baseActivity);
   void inject(RegistrationIntentService registrationIntentService);

}
