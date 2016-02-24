package rocks.itsnotrocketscience.bejay.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.music.model.Track;
import rocks.itsnotrocketscience.bejay.search.di.DaggerSearchComponent;
import rocks.itsnotrocketscience.bejay.search.di.SearchComponent;
import rocks.itsnotrocketscience.bejay.search.di.SearchModule;

/**
 * Created by nemi on 20/02/2016.
 */
public class SearchActivity extends InjectedActivity<SearchComponent> implements  SearchView.OnQueryTextListener, SearchFragment.OnModelSelectedListener {

    public static final String EXTRA_TRACK = "track";

    @Bind(R.id.toolbar) Toolbar toolbar;

    SearchModule searchModule;
    SearchComponent searchComponent;
    String query;

    MenuItem addTrackMenuItem;
    SearchView trackSearchView;

    public SearchActivity() {
        this.searchModule = new SearchModule(this);
    }

    @Override
    public SearchComponent getComponent() {
        if(searchComponent == null) {
        return searchComponent = DaggerSearchComponent.builder()
                .searchModule(searchModule)
                .activityComponent(getActivityComponent())
                .build();
        }

        return searchComponent;
    }

    private ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
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
//            Artist artist = new Artist();
//            artist.setId("27");
//            getSupportFragmentManager().beginTransaction().add(R.id.search_result_container, ArtistDetailsFragment.newInstance(artist)).commitAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().add(R.id.search_result_container, SearchFragment.newInstance(query, Model.TYPE_TRACK)).commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onModelSelected(Model model) {
        switch (model.getType()) {
            case Model.TYPE_TRACK : {
                Track track = (Track) model;
                Intent result = new Intent().putExtra(EXTRA_TRACK, track);
                setResult(RESULT_OK, result);
                finish();
                break;
            }
        }
    }
}
