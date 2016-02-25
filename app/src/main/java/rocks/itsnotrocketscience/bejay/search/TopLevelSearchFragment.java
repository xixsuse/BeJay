package rocks.itsnotrocketscience.bejay.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.search.contract.MusicSearchContract;
import rocks.itsnotrocketscience.bejay.search.contract.TopLevelSearchContract;
import rocks.itsnotrocketscience.bejay.view.ItemTouchHelper;

import static android.app.SearchManager.QUERY;

public class TopLevelSearchFragment extends BaseFragment implements TopLevelSearchContract.View, SearchView.OnQueryTextListener, ItemTouchHelper.OnItemClickedListener {
    public static final String EXTRA_SECTION_SIZE = "section_size";

    @Bind(R.id.search_result) RecyclerView result;
    @Bind(R.id.progress) ProgressBar progressIndicator;

    @Inject TopLevelSearchContract.Presenter presenter;
    @Inject TopLevelSearchResultAdapter adapter;

    private MenuItem searchMenuItem;
    private SearchView searchView;
    private String query;
    private int sectionSize;
    private ItemTouchHelper itemTouchHelper;
    private MusicSearchContract contract;

    public static TopLevelSearchFragment newInstance(int sectionSize) {
        TopLevelSearchFragment fragment = new TopLevelSearchFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRA_SECTION_SIZE, sectionSize);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contract = (MusicSearchContract) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contract = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.query = query;
        adapter.reset();
        presenter.search(query, sectionSize);
        MenuItemCompat.collapseActionView(searchMenuItem);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        query = args.getString(QUERY);
        sectionSize = args.getInt(EXTRA_SECTION_SIZE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.track_search, menu);
        searchMenuItem = menu.findItem(R.id.action_track_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setOnQueryTextListener(this);

        if(TextUtils.isEmpty(query)) {
            MenuItemCompat.expandActionView(searchMenuItem);
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
        result.setLayoutManager(new LinearLayoutManager(getContext()));
        result.setAdapter(adapter);
        itemTouchHelper = new ItemTouchHelper(getContext());
        itemTouchHelper.setOnItemClickedListener(this);
        itemTouchHelper.setup(result);

    }

    @Override
    public void showSearchResults(TopLevelSearchContract.SearchResult searchResult) {
        adapter.setSearchResult(searchResult);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onViewAttached(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onViewDetached();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int adapterPosition) {
        Model model = adapter.getModel(adapterPosition);
        if(model != null) {
            contract.onModelSelected(model);
        } else {
            int type = adapter.getShowMoreType(adapterPosition);
            if(type != Model.TYPE_UNKNOWN) {
                contract.searchTracks(type, query);
            }

        }
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
