package rocks.itsnotrocketscience.bejay.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.dagger.ActivityModule;
import rocks.itsnotrocketscience.bejay.dagger.DaggerActivityComponent;
import rocks.itsnotrocketscience.bejay.music.model.Artist;
import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.music.model.Track;
import rocks.itsnotrocketscience.bejay.search.contract.MusicSearchContract;
import rocks.itsnotrocketscience.bejay.search.di.DaggerSearchComponent;
import rocks.itsnotrocketscience.bejay.search.di.SearchComponent;
import rocks.itsnotrocketscience.bejay.search.di.SearchModule;

/**
 * Created by nemi on 20/02/2016.
 */
public class SearchActivity extends InjectedActivity<SearchComponent> implements MusicSearchContract {

    public static final String EXTRA_TRACK = "track";

    @Bind(R.id.toolbar) Toolbar toolbar;

    SearchModule searchModule;
    SearchComponent searchComponent;

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

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.search_result_container, TopLevelSearchFragment.newInstance(3))
                    .commitAllowingStateLoss();
        }
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
    public void onModelSelected(Model model) {
        switch (model.getType()) {
            case Model.TYPE_TRACK : {
                Track track = (Track) model;
                Intent result = new Intent().putExtra(EXTRA_TRACK, track);
                setResult(RESULT_OK, result);
                finish();
                break;
            }
            case Model.TYPE_ARTIST : {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.search_result_container, ArtistDetailsFragment.newInstance((Artist) model))
                        .addToBackStack("artist")
                        .commitAllowingStateLoss();
                break;
            }
            case Model.TYPE_PLAYLIST : {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.search_result_container, PlaylistDetailsFragment.newInstance(model.getId()))
                        .addToBackStack("playlist-details")
                        .commitAllowingStateLoss();
                break;
            }
            case Model.TYPE_ALBUM : {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.search_result_container, AlbumDetailsFragment.newInstance(model.getId()))
                        .addToBackStack("album-details")
                        .commitAllowingStateLoss();
                break;
            }
        }
    }

    @Override
    public void searchTracks(@Model.Type int type, String query) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.search_result_container, SearchFragment.newInstance(query, type))
                .addToBackStack("search")
                .commitAllowingStateLoss();
    }


}
