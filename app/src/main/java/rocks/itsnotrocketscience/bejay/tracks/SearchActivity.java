package rocks.itsnotrocketscience.bejay.tracks;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.dagger.ActivityModule;
import rocks.itsnotrocketscience.bejay.dagger.DaggerActivityComponent;
import rocks.itsnotrocketscience.bejay.tracks.search.TrackSearchContract;
import rocks.itsnotrocketscience.bejay.view.ItemTouchHelper;

import static android.support.v7.appcompat.R.attr.listPreferredItemHeight;

/**
 * Created by nemi on 20/02/2016.
 */
public class SearchActivity extends InjectedActivity<ActivityComponent> implements
        TrackSearchContract.View, ItemTouchHelper.OnItemClickedListener, SearchView.OnQueryTextListener {
    private static final String TAG = "SearchActivity";
    public static final String EXTRA_TRACK = "track";

    @Inject SearchPresenter searchPresenter;
    @Inject TrackListAdapter trackListAdapter;
    @Bind(R.id.track_list) RecyclerView trackList;
    @Bind(R.id.toolbar) Toolbar toolbar;

    ActivityModule activityModule;
    ActivityComponent activityComponent;
    LinearLayoutManager layoutManager;
    String query;
    ItemTouchHelper itemTouchHelper;
    MenuItem addTrackMenuItem;
    SearchView trackSearchView;

    public SearchActivity() {
        this.activityModule = new ActivityModule(this);
    }

    @Override
    public ActivityComponent getComponent() {
        return activityComponent = DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .appComponent(getAppComponent())
                .build();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        setContentView(R.layout.activity_track_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);

        trackList.setLayoutManager(layoutManager);
        trackList.setAdapter(trackListAdapter);
        trackList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) {
                    int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                    int itemCount = trackListAdapter.getItemCount();
                    if(lastVisiblePosition == itemCount-1) {
                        searchPresenter.loadMoreResults();
                    }
                }
            }
        });

        itemTouchHelper = new ItemTouchHelper(this);
        itemTouchHelper.setup(trackList);
        itemTouchHelper.setOnItemClickedListener(this);

        if(savedInstanceState != null) {
            query = savedInstanceState.getString(SearchManager.QUERY);
        }

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        }

        searchPresenter.onViewAttached(this);

        if(query != null) {
            searchPresenter.search(query, getPageSize());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(SearchManager.QUERY, query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.track_search, menu);
        addTrackMenuItem = menu.findItem(R.id.action_track_search);
        trackSearchView = (SearchView) MenuItemCompat.getActionView(addTrackMenuItem);
        trackSearchView.setOnQueryTextListener(this);

        if(TextUtils.isEmpty(query)) {
            MenuItemCompat.expandActionView(addTrackMenuItem);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;
        switch (item.getItemId()) {
            case android.R.id.home : {
                NavUtils.navigateUpFromSameTask(this);
                handled = true;
                break;
            }
            default : {
                handled = super.onOptionsItemSelected(item);
            }
        }

        return handled;
    }

    float getTrackItemMinHeight(DisplayMetrics displayMetrics) {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(listPreferredItemHeight, typedValue, true);

        return typedValue.getDimension(displayMetrics);
    }

    int getPageSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        float height = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, displayMetrics);

        float itemHeight = getTrackItemMinHeight(displayMetrics);

        int itemCount = (int)(height / itemHeight);

        if(itemCount * itemHeight < height) {
            itemCount++;
        }

        return itemCount;
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int adapterPosition) {
        Track track = trackListAdapter.getTrack(adapterPosition);
        Intent result = new Intent();
        result.putExtra(EXTRA_TRACK, track);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchPresenter.onViewDetached();
    }

    @Override
    public void onSearchSuggestionsLoaded(Cursor cursor) {

    }

    @Override
    public void onSearchResultsLoaded(List<Track> tracks) {
        trackListAdapter.addAll(tracks);
    }

    @Override
    public void setSearchProgressVisible(boolean visible) {

    }

    @Override
    public void onError() {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchPresenter.search(query, getPageSize());
        MenuItemCompat.collapseActionView(addTrackMenuItem);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }
}
