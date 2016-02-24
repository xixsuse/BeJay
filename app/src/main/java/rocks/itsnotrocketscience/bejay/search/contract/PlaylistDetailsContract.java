package rocks.itsnotrocketscience.bejay.search.contract;


import rocks.itsnotrocketscience.bejay.music.model.Playlist;

public interface PlaylistDetailsContract {
    interface View {
        void onPlaylistLoaded(Playlist playlist);
    }

    interface Presenter extends rocks.itsnotrocketscience.bejay.search.contract.Presenter<View> {
        void loadPlaylist(String id);
    }
}
