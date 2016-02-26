package rocks.itsnotrocketscience.bejay.search.di;

import dagger.Component;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.music.model.Album;
import rocks.itsnotrocketscience.bejay.music.model.Artist;
import rocks.itsnotrocketscience.bejay.music.model.Playlist;
import rocks.itsnotrocketscience.bejay.music.model.Track;
import rocks.itsnotrocketscience.bejay.search.AlbumDetailsFragment;
import rocks.itsnotrocketscience.bejay.search.ArtistDetailsFragment;
import rocks.itsnotrocketscience.bejay.search.PlaylistDetailsFragment;
import rocks.itsnotrocketscience.bejay.search.SearchActivity;
import rocks.itsnotrocketscience.bejay.search.SearchFragment;
import rocks.itsnotrocketscience.bejay.search.TopLevelSearchFragment;
import rocks.itsnotrocketscience.bejay.search.presenter.SearchPresenter;
import rocks.itsnotrocketscience.bejay.search.presenter.TopLevelSearchPresenter;
import rocks.itsnotrocketscience.bejay.search.view.AlbumViewHolder;
import rocks.itsnotrocketscience.bejay.search.view.ArtistViewHolder;
import rocks.itsnotrocketscience.bejay.search.view.PlaylistViewHolder;
import rocks.itsnotrocketscience.bejay.search.view.TrackViewHolder;

@Search
@Component(dependencies = ActivityComponent.class, modules = {SearchModule.class})
public interface SearchComponent {
    void inject(SearchActivity searchActivity);
    void inject(SearchFragment searchFragment);
    void inject(AlbumViewHolder albumViewHolder);
    void inject(ArtistViewHolder artistViewHolder);
    void inject(TrackViewHolder trackViewHolder);
    void inject(PlaylistViewHolder playlistViewHolder);
    void inject(ArtistDetailsFragment artistDetailsFragment);
    void inject(TopLevelSearchFragment fragment);
    void inject(AlbumDetailsFragment fragment);
    void inject(PlaylistDetailsFragment fragment);

    SearchPresenter<Track> trackSearchPresenter();
    SearchPresenter<Album> albumSearchPresenter();
    SearchPresenter<Artist> artistSearchPresenter();
    SearchPresenter<Playlist> playlistSearchPresenter();

    TopLevelSearchPresenter topLevelSearchPresenter();
}
