package rocks.itsnotrocketscience.bejay.dagger;


import android.content.res.Resources;
import android.view.LayoutInflater;

import dagger.Component;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;
import rocks.itsnotrocketscience.bejay.deezer.api.Artist;
import rocks.itsnotrocketscience.bejay.deezer.api.Search;
import rocks.itsnotrocketscience.bejay.event.list.EventListFragment;
import rocks.itsnotrocketscience.bejay.event.single.EventActivity;
import rocks.itsnotrocketscience.bejay.event.single.EventFragment;
import rocks.itsnotrocketscience.bejay.home.HomeFragment;
import rocks.itsnotrocketscience.bejay.main.MainActivity;
import rocks.itsnotrocketscience.bejay.main.NavigationDrawerFragment;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

@PerActivity
@Component(modules = ActivityModule.class, dependencies = {AppComponent.class})
public interface ActivityComponent {
    Launcher launcher();
    LayoutInflater layoutInflater();
    Resources resources();
    Search deezerSearchApi();
    Artist deezerArtistApi();

    /**
     * Injectors
     * */
    void inject(BaseActivity baseActivity);
    void inject(MainActivity mainActivity);
    void inject(EventListFragment eventListFragment);
    void inject(EventFragment eventFragment);
    void inject(EventActivity eventActivity);
    void inject(HomeFragment homeFragment);
    void inject(NavigationDrawerFragment navigationDrawerFragment);
}
