package rocks.itsnotrocketscience.bejay.tracks;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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

import static android.support.v7.appcompat.R.attr.listPreferredItemHeight;

/**
 * Created by nemi on 20/02/2016.
 */
public class SearchActivity extends InjectedActivity<ActivityComponent> implements TrackSearchContract.View {

    @Inject SearchPresenter searchPresenter;
    @Inject TrackListAdapter trackListAdapter;
    @Bind(R.id.track_list) RecyclerView trackList;

    ActivityModule activityModule;
    ActivityComponent activityComponent;
    LinearLayoutManager layoutManager;
    String query;

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

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        }


        searchPresenter.onViewAttached(this);
        searchPresenter.search(query, getPageSize());
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

        float height = trackList.getHeight();
        height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, displayMetrics);

        float itemHeight = getTrackItemMinHeight(displayMetrics);

        int itemCount = (int)(height / itemHeight);

        if(itemCount * itemHeight < height) {
            itemCount++;
        }

        return itemCount;
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
}
