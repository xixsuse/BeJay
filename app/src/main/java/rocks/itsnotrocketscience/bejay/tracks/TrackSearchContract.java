package rocks.itsnotrocketscience.bejay.tracks;

import android.database.Cursor;

import java.util.List;

import rocks.itsnotrocketscience.bejay.deezer.model.Track;

/**
 * Created by nemi on 20/02/2016.
 */
public interface TrackSearchContract {
    interface Presenter {
        /**
         * Load search suggestion for query
         * */
        void loadSearchSuggestions(String query);

        /**
         * Initiate search
         *
         * @param query search query string
         * @param pageSize page size for paginated search.
         *                 Note that this is just a hint, implementations may disregard this parameter
         * */
        void search(String query, int pageSize);

        /**
         * Load additional results for the current search
         * */
        void loadMoreResults();

        /**
         * Called when view is attached
         * */
        void onViewAttached(View view);

        /**
         * Called when view is detached
         * */
        void onViewDetached();
    }

    interface View {
        /**
         * TrackSearchManager suggestions loaded. Note a cursor is passed,
         * as search view requires a cursor adapter to be used for search suggestions
         * */
        void onSearchSuggestionsLoaded(Cursor cursor);

        /**
         * Called when additional search results are ready.
         * */
        void onSearchResultsLoaded(List<Track> tracks);

        /**
         * Toggle search progress indicator visibility
         * */
        void setSearchProgressVisible(boolean visible);

        /**
         * Display error
         * */
        void onError();
    }
}
