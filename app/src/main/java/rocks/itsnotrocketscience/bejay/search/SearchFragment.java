package rocks.itsnotrocketscience.bejay.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.base.InjectedFragment;
import rocks.itsnotrocketscience.bejay.search.di.SearchComponent;
import rocks.itsnotrocketscience.bejay.search.model.Model;
import rocks.itsnotrocketscience.bejay.view.ItemTouchHelper;

public class SearchFragment extends InjectedFragment<SearchComponent> implements SearchContract.View, ItemTouchHelper.OnItemClickedListener {
    public static final String EXTRA_PAGE_SIZE = "page_size";

    @Inject ModelAdapter resultAdapter;
    @Bind(R.id.search_result) RecyclerView searchResult;

    SearchContract.Presenter searchPresenter;

    LinearLayoutManager layoutManager;
    ItemTouchHelper itemTouchHelper;
    String query;
    boolean loading;
    OnModelSelectedListener onModelSelectedListener;

    public interface OnModelSelectedListener {
        void onModelSelected(Model model);
    }

    float getTrackItemMinHeight() {
        return getResources().getDimension(R.dimen.list_item_prefered_height);
    }

    int getPageSize() {
        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey(EXTRA_PAGE_SIZE)) {
            return arguments.getInt(EXTRA_PAGE_SIZE);
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        float height = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, displayMetrics);

        float itemHeight = getTrackItemMinHeight();

        int itemCount = (int)(height / itemHeight);

        if(itemCount * itemHeight < height) {
            itemCount++;
        }

        return itemCount;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onModelSelectedListener = (OnModelSelectedListener) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        onModelSelectedListener = null;
    }

    @Override
    public SearchComponent getComponent() {
        InjectedActivity<SearchComponent> activity = (InjectedActivity<SearchComponent>) getActivity();
        return activity.getComponent();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        query = getArguments().getString(SearchManager.QUERY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        layoutManager = new LinearLayoutManager(getContext());
        searchResult.setAdapter(resultAdapter);
        searchResult.setLayoutManager(layoutManager);

        searchResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) {
                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                    int itemCount = layoutManager.getItemCount();
                    if(lastVisiblePosition == itemCount - 1 && !loading) {
                        searchPresenter.loadMoreResults();
                        loading = true;
                    }
                }
            }
        });

        itemTouchHelper = new ItemTouchHelper(getContext());
        itemTouchHelper.setup(searchResult);
        itemTouchHelper.setOnItemClickedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchPresenter.onViewAttached(this);
        searchPresenter.search(query, getPageSize());
    }

    @Override
    public void onPause() {
        super.onPause();
        searchPresenter.onViewDetached();
    }

    @Override
    public void onSearchResultsLoaded(List<? extends Model> modelItems) {
        resultAdapter.addAll(modelItems);
        loading = false;
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int adapterPosition) {
        onModelSelectedListener.onModelSelected(resultAdapter.getModel(adapterPosition));
    }
}
