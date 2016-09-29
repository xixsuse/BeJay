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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.search.contract.MusicSearchContract;
import rocks.itsnotrocketscience.bejay.search.contract.SearchContract;
import rocks.itsnotrocketscience.bejay.search.di.SearchComponent;
import rocks.itsnotrocketscience.bejay.view.ItemTouchHelper;

public class SearchFragment extends BaseFragment implements SearchContract.View, ItemTouchHelper.OnItemClickedListener {
    private static final String EXTRA_PAGE_SIZE = "page_size";
    private static final String EXTRA_TYPE = "type";

    @Inject public ModelAdapter resultAdapter;

    @Bind(R.id.search_result)
    public RecyclerView searchResult;
    @Bind(R.id.progress)
    public ProgressBar progressIndicator;

    private SearchContract.Presenter searchPresenter;
    private LinearLayoutManager layoutManager;
    private ItemTouchHelper itemTouchHelper;
    private int type;
    private String query;
    private boolean loading;
    private MusicSearchContract contract;

    private float getTrackItemMinHeight() {
        return getResources().getDimension(R.dimen.list_item_prefered_height);
    }

    private int getPageSize() {
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

    public static SearchFragment newInstance(String query, int type) {
        SearchFragment fragment = new SearchFragment();

        Bundle args = new Bundle();
        args.putString(SearchManager.QUERY, query);
        args.putInt(EXTRA_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contract = (MusicSearchContract) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        contract = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchComponent component = getComponent();
        component.inject(this);
        Bundle args = getArguments();

        query = args.getString(SearchManager.QUERY);
        type = args.getInt(EXTRA_TYPE);
        switch (type) {
            case Model.TYPE_TRACK : {
                this.searchPresenter = component.trackSearchPresenter();
                break;
            }
            case Model.TYPE_ALBUM : {
                this.searchPresenter = component.albumSearchPresenter();
                break;
            }
            case Model.TYPE_ARTIST : {
                this.searchPresenter = component.artistSearchPresenter();
                break;
            }
            case Model.TYPE_PLAYLIST : {
                this.searchPresenter = component.artistSearchPresenter();
                break;
            }
            default : {
                throw new IllegalArgumentException("unsupported type("+type+")");
            }
        }
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
        contract.onModelSelected(resultAdapter.getItem(adapterPosition));
    }

    @Override
    public void setProgressVisible(boolean visible) {
        if(visible) {
            progressIndicator.setVisibility(View.VISIBLE);
        } else {
            progressIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), "request failed", Toast.LENGTH_SHORT).show();
    }
}
