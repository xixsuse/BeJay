package rocks.itsnotrocketscience.bejay.search.contract;

import java.util.List;

import rocks.itsnotrocketscience.bejay.music.model.Album;
import rocks.itsnotrocketscience.bejay.music.model.Artist;
import rocks.itsnotrocketscience.bejay.music.model.Playlist;
import rocks.itsnotrocketscience.bejay.music.model.Track;

public interface TopLevelSearchContract {
    class SearchResult {
        private List<Track> tracks;
        private List<Album> albums;
        private List<Artist> artists;
        private List<Playlist> playlists;

        public List<Track> getTracks() {
            return tracks;
        }

        public void setTracks(List<Track> tracks) {
            this.tracks = tracks;
        }

        public List<Album> getAlbums() {
            return albums;
        }

        public void setAlbums(List<Album> albums) {
            this.albums = albums;
        }

        public List<Artist> getArtists() {
            return artists;
        }

        public void setArtists(List<Artist> artists) {
            this.artists = artists;
        }

        public List<Playlist> getPlaylists() {
            return playlists;
        }

        public void setPlaylists(List<Playlist> playlists) {
            this.playlists = playlists;
        }
    }

    interface Presenter extends rocks.itsnotrocketscience.bejay.search.contract.Presenter<View> {
        void search(String query, long pageSize);
    }

    interface View {
        void showSearchResults(SearchResult searchResult);
        void setProgressVisible(boolean visible);
        void showError();
    }
}
