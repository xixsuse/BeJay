package rocks.itsnotrocketscience.bejay.search;

import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.search.model.ArtistDetails;

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
