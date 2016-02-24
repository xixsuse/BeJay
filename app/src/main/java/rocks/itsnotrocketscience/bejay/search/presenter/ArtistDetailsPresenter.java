package rocks.itsnotrocketscience.bejay.search.presenter;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.music.Api;

import rocks.itsnotrocketscience.bejay.music.model.Artist;
import rocks.itsnotrocketscience.bejay.music.model.ArtistDetails;

import rocks.itsnotrocketscience.bejay.search.contracat.ArtistDetailsContract;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class ArtistDetailsPresenter implements ArtistDetailsContract.Presenter {

    ArtistDetailsContract.View view;
    Api.Artist api;


    @Inject
    public ArtistDetailsPresenter(Api.Artist api) {
        this.api= api;
    }

    @Override
    public void loadArtist(Artist artist) {
        Long id = Long.valueOf(artist.getId());
        Observable.combineLatest(api.topTracks(artist.getId()), api.albums(artist.getId()), (topTracks, discography) -> {
            ArtistDetails artistDetails = new ArtistDetails();
            artistDetails.setArtist(artist);
            artistDetails.setTopTracks(topTracks);
            artistDetails.setDiscography(discography);
            return artistDetails;
        }).first().observeOn(AndroidSchedulers.mainThread())
                .subscribe(artistDetails -> view.onLoaded(artistDetails));

    }

    @Override
    public void onViewAttached(ArtistDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }
}
