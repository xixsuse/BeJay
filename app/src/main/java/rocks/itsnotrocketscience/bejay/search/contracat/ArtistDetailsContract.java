package rocks.itsnotrocketscience.bejay.search.contracat;

import rocks.itsnotrocketscience.bejay.music.model.Artist;
import rocks.itsnotrocketscience.bejay.music.model.ArtistDetails;


public interface ArtistDetailsContract {
    interface Presenter {
        void onViewAttached(View view);
        void onViewDetached();
        void loadArtist(Artist artist);
    }

    interface View {
        void onLoaded(ArtistDetails artistDetails);
    }
}
