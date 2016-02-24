package rocks.itsnotrocketscience.bejay.search.contract;

import rocks.itsnotrocketscience.bejay.music.model.Album;

public interface AlbumDetailsContract {
    interface View {
        void onAlbumLoaded(Album album);
    }

    interface Presenter extends rocks.itsnotrocketscience.bejay.search.contract.Presenter<View> {
        void loadAlbum(String id);
    }
}
