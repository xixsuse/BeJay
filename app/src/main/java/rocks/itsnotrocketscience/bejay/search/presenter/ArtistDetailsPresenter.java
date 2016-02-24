package rocks.itsnotrocketscience.bejay.search.presenter;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.music.Api;
import rocks.itsnotrocketscience.bejay.search.contract.ArtistDetailsContract;
import rocks.itsnotrocketscience.bejay.search.contract.PresenterBase;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class ArtistDetailsPresenter extends PresenterBase<ArtistDetailsContract.View> implements ArtistDetailsContract.Presenter {

    private Api.Artist api;

    @Inject
    public ArtistDetailsPresenter(Api.Artist api) {
        this.api= api;
    }

    @Override
    public void loadArtistDetails(String id) {
        Observable.combineLatest(api.topTracks(id), api.albums(id), (topTracks, discography) -> {
            ArtistDetailsContract.ArtistDetails artistDetails = new ArtistDetailsContract.ArtistDetails();
            artistDetails.setTopTracks(topTracks);
            artistDetails.setDiscography(discography);
            return artistDetails;
        }).first().compose(onDetach()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(artistDetails -> getView().onLoaded(artistDetails));

    }
}
