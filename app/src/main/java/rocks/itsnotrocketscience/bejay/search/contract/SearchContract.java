package rocks.itsnotrocketscience.bejay.search.contract;


import java.util.List;

import rocks.itsnotrocketscience.bejay.music.model.Model;

/**
 * Created by nemi on 20/02/2016.
 */
public interface SearchContract {
    interface Presenter extends rocks.itsnotrocketscience.bejay.search.contract.Presenter<View> {
        void search(String query, long pageSize);
        void loadMoreResults();
    }

    interface View {
        void onSearchResultsLoaded(List<? extends Model> tracks);
        void setProgressVisible(boolean visible);
        void showError();
    }
}
