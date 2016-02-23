package rocks.itsnotrocketscience.bejay.search;

import java.util.List;

import rocks.itsnotrocketscience.bejay.search.model.Model;

/**
 * Created by nemi on 20/02/2016.
 */
public interface SearchContract<T> {
    interface Presenter<T> {
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
         * Called when additional search results are ready.
         * */
        void onSearchResultsLoaded(List<? extends Model> tracks);
    }
}
