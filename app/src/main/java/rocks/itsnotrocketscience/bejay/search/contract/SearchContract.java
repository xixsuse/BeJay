package rocks.itsnotrocketscience.bejay.search.contract;


import java.util.List;

import rocks.itsnotrocketscience.bejay.music.model.Model;

/**
 * Created by nemi on 20/02/2016.
 */
public interface SearchContract {
    interface Presenter extends rocks.itsnotrocketscience.bejay.search.contract.Presenter<View> {
        /**
         * Initiate search
         *
         * @param query search query string
         * @param pageSize page size for paginated search.
         *                 Note that this is just a hint, implementations may disregard this parameter
         * */
        void search(String query, long pageSize);

        /**
         * Load additional results for the current search
         * */
        void loadMoreResults();
    }

    interface View {
        /**
         * Called when additional search results are ready.
         * */
        void onSearchResultsLoaded(List<? extends Model> tracks);
    }
}
