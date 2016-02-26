package rocks.itsnotrocketscience.bejay.search.contract;

import java.util.List;

import rocks.itsnotrocketscience.bejay.music.model.Album;
import rocks.itsnotrocketscience.bejay.music.model.Track;


public interface ArtistDetailsContract {
    class ArtistDetails {
        private List<Track> topTracks;
        private List<Album> discography;

        public List<Track> getTopTracks() {
            return topTracks;
        }

        public void setTopTracks(List<Track> topTracks) {
            this.topTracks = topTracks;
        }

        public List<Album> getDiscography() {
            return discography;
        }

        public void setDiscography(List<Album> discography) {
            this.discography = discography;
        }
    }

    interface Presenter extends rocks.itsnotrocketscience.bejay.search.contract.Presenter<View>{
        void loadArtistDetails(String id);
    }

    interface View {
        void onLoaded(ArtistDetails artistDetails);
        void setProgressVisible(boolean visible);
        void showError();
    }
}
