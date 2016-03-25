package rocks.itsnotrocketscience.bejay.dagger;


import android.content.res.Resources;
import android.view.LayoutInflater;

import dagger.Component;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;
import rocks.itsnotrocketscience.bejay.event.create.EventCreateActivity;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Album;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Artist;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Playlist;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Search;
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
    Album deezerAlbumApi();
    Playlist deezrPlaylistApi();

    /**
     * Injectors
     * */
    void inject(BaseActivity baseActivity);
    void inject(EventCreateActivity eventCreateActivity);
    void inject(MainActivity mainActivity);
    void inject(EventListFragment eventListFragment);
    void inject(EventFragment eventFragment);
    void inject(EventActivity eventActivity);
    void inject(HomeFragment homeFragment);
    void inject(NavigationDrawerFragment navigationDrawerFragment);
}
