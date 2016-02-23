package rocks.itsnotrocketscience.bejay.search;

import android.app.SearchManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.dagger.ActivityModule;
import rocks.itsnotrocketscience.bejay.dagger.DaggerActivityComponent;
import rocks.itsnotrocketscience.bejay.search.model.Album;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.search.model.Playlist;
import rocks.itsnotrocketscience.bejay.search.model.Track;

/**
 * Created by nemi on 20/02/2016.
 */
public class SearchActivity extends InjectedActivity<ActivityComponent> implements  SearchView.OnQueryTextListener {

    public static final String EXTRA_TRACK = "track";

    public static class TrackSearchFragment extends SearchFragment<Track> {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            getComponent().inject(this);
            super.onCreate(savedInstanceState);
        }

        public static TrackSearchFragment newInstance(String query) {
            TrackSearchFragment fragment = new TrackSearchFragment();
            Bundle args = new Bundle();
            args.putString(SearchManager.QUERY, query);
            fragment.setArguments(args);
            return fragment;
        }
    }

    public static class ArtistSearchFragment extends SearchFragment<Artist> {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            getComponent().inject(this);
            super.onCreate(savedInstanceState);
        }

        public static ArtistSearchFragment newInstance(String query) {
            ArtistSearchFragment fragment = new ArtistSearchFragment();
            Bundle args = new Bundle();
            args.putString(SearchManager.QUERY, query);
            fragment.setArguments(args);
            return fragment;
        }
    }

    public static class AlbumSearchFragment extends SearchFragment<Album> {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            getComponent().inject(this);
            super.onCreate(savedInstanceState);
        }

        public static AlbumSearchFragment newInstance(String query) {
            AlbumSearchFragment fragment = new AlbumSearchFragment();
            Bundle args = new Bundle();
            args.putString(SearchManager.QUERY, query);
            fragment.setArguments(args);
            return fragment;
        }
    }

    public static class PlaylistSearchFragment extends SearchFragment<Playlist> {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            getComponent().inject(this);
            super.onCreate(savedInstanceState);
        }

        public static PlaylistSearchFragment newInstance(String query) {
            PlaylistSearchFragment fragment = new PlaylistSearchFragment();
            Bundle args = new Bundle();
            args.putString(SearchManager.QUERY, query);
            fragment.setArguments(args);
            return fragment;
        }
    }

    @Bind(R.id.toolbar) Toolbar toolbar;

    ActivityModule activityModule;
    ActivityComponent activityComponent;
    String query;

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

        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState != null) {
            query = savedInstanceState.getString(SearchManager.QUERY);
        } else {
            query = getIntent().getStringExtra(SearchManager.QUERY);
            showTrackSearchResults(query);
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
                setResult(RESULT_CANCELED);
                finish();
                handled = true;
                break;
            }
            default : {
                handled = super.onOptionsItemSelected(item);
            }
        }

        return handled;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.query = query;
        showTrackSearchResults(query);
        MenuItemCompat.collapseActionView(addTrackMenuItem);
        return true;
    }

    private void showTrackSearchResults(String query) {
        if(!TextUtils.isEmpty(query)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.search_result_container, PlaylistSearchFragment.newInstance(query))
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }
}
