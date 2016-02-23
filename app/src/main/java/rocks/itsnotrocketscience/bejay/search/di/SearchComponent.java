package rocks.itsnotrocketscience.bejay.search.di;

import dagger.Component;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.search.AlbumViewHolder;
import rocks.itsnotrocketscience.bejay.search.ArtistDetailsFragment;
import rocks.itsnotrocketscience.bejay.search.ArtistViewHolder;
import rocks.itsnotrocketscience.bejay.search.PlaylistViewHolder;
import rocks.itsnotrocketscience.bejay.search.SearchActivity;
import rocks.itsnotrocketscience.bejay.search.SearchFragment;
import rocks.itsnotrocketscience.bejay.search.SearchPresenter;
import rocks.itsnotrocketscience.bejay.search.TopLevelSearchPresenter;
import rocks.itsnotrocketscience.bejay.search.TrackViewHolder;
import rocks.itsnotrocketscience.bejay.search.model.Album;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.search.model.Playlist;
import rocks.itsnotrocketscience.bejay.search.model.Track;

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

    TopLevelSearchPresenter topLevelSearchPresenter();
    SearchPresenter<Track> trackSearchPresenter();
    SearchPresenter<Album> albumSearchPresenter();
    SearchPresenter<Artist> artistSearchPresenter();
    SearchPresenter<Playlist> playlistSearchPresenter();
}
