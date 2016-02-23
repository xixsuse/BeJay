package rocks.itsnotrocketscience.bejay.search;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.base.InjectedFragment;
import rocks.itsnotrocketscience.bejay.search.di.SearchComponent;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.search.model.ArtistDetails;

public class ArtistDetailsFragment extends InjectedFragment<SearchComponent> implements ArtistDetailsContract.View {

    @Inject ArtistDetailAdapter artistDetailAdapter;
    @Inject ArtistDetailsPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        presenter.onViewAttached(this);

        Artist artist = new Artist();
        artist.setId("27");
        presenter.loadArtist(artist);
    }

    @Override
    public void onLoaded(ArtistDetails artistDetails) {
        artistDetailAdapter.setArtistDetails(artistDetails);
    }

    @Override
    public SearchComponent getComponent() {
        InjectedActivity<SearchComponent> searchActivity = (InjectedActivity<SearchComponent>) getActivity();
        return searchActivity.getComponent();
    }
}
