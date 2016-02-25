package rocks.itsnotrocketscience.bejay.search.presenter;

import rocks.itsnotrocketscience.bejay.music.Api;
import rocks.itsnotrocketscience.bejay.search.contract.PlaylistDetailsContract;
import rocks.itsnotrocketscience.bejay.search.contract.PresenterBase;
import rx.android.schedulers.AndroidSchedulers;

public class PlaylistDetailsPresenter extends PresenterBase<PlaylistDetailsContract.View> implements PlaylistDetailsContract.Presenter  {
    private Api.Playlist api;

    public PlaylistDetailsPresenter(Api.Playlist api) {
        this.api = api;
    }

    @Override
    public void loadPlaylist(String id) {
        getView().setProgressVisible(true);
        api.get(id).compose(onDetach()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(playlist -> getView().onPlaylistLoaded(playlist),
                        (error) -> getView().showError(),
                        () -> getView().setProgressVisible(false));
    }
}
