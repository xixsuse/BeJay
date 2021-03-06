package rocks.itsnotrocketscience.bejay.search.presenter;

import rocks.itsnotrocketscience.bejay.music.Api;
import rocks.itsnotrocketscience.bejay.search.contract.AlbumDetailsContract;
import rocks.itsnotrocketscience.bejay.search.contract.PresenterBase;
import rx.android.schedulers.AndroidSchedulers;

public class AlbumDetailsPresenter extends PresenterBase<AlbumDetailsContract.View> implements AlbumDetailsContract.Presenter {

    private final Api.Album api;

    public AlbumDetailsPresenter(Api.Album api) {
        this.api = api;

    }

    @Override
    public void loadAlbum(String id) {
        getView().setProgressVisible(true);
        api.get(id).compose(onDetach())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(album -> getView().onAlbumLoaded(album),
                        (error) -> getView().showError(),
                        () -> getView().setProgressVisible(false));
    }

}
