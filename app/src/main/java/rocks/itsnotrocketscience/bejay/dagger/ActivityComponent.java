package rocks.itsnotrocketscience.bejay.dagger;


import dagger.Component;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;
import rocks.itsnotrocketscience.bejay.event.list.EventListFragment;
import rocks.itsnotrocketscience.bejay.event.single.EventActivity;
import rocks.itsnotrocketscience.bejay.event.single.EventFragment;
import rocks.itsnotrocketscience.bejay.home.HomeFragment;
import rocks.itsnotrocketscience.bejay.main.NavigationDrawerFragment;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

@PerActivity
@Component(modules = ActivityModule.class, dependencies = {AppComponent.class})
public interface ActivityComponent {
    Launcher launcher();

    /**
     * Injectors
     * */
    void inject(BaseActivity baseActivity);
    void inject(EventListFragment eventListFragment);
    void inject(EventFragment eventFragment);
    void inject(EventActivity eventActivity);
    void inject(HomeFragment homeFragment);
    void inject(NavigationDrawerFragment navigationDrawerFragment);
}
