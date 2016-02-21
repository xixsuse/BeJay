package rocks.itsnotrocketscience.bejay.event.single;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.ApiManager;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;
import rocks.itsnotrocketscience.bejay.tracks.SearchActivity;
import rocks.itsnotrocketscience.bejay.tracks.Track;

/**
 * A placeholder fragment containing a simple view.
 */
public class EventFragment extends BaseFragment<ActivityComponent> implements EventContract.EventView, SearchView.OnQueryTextListener {

    static final int RC_SEARCH_TRACK = 1;

    @Inject EventContract.EventPresenter presenter;
    @Inject AccountManager accountManager;
    @Inject ApiManager apiManager;
    @Inject Events events;

    @Bind(R.id.rvSongList) RecyclerView rvSongList;

    SongListAdapter adapter;
    List<Song> songList;

    MenuItem addTrackMenuItem;
    SearchView trackSearchView;

    public EventFragment() {
        songList = new ArrayList<>();
    }

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadEvent(((EventActivity) getActivity()).getIdFromBundle());
        setupViews();
    }

    private void setupViews() {
        rvSongList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvSongList.setLayoutManager(llm);
        adapter = new SongListAdapter(songList);
        adapter.setItemClickListener((view1, position) -> Log.d("yo", "yo"));
        rvSongList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getComponent().inject(this);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.event, menu);
        addTrackMenuItem = menu.findItem(R.id.action_add_track);
        trackSearchView = (SearchView) MenuItemCompat.getActionView(addTrackMenuItem);
        trackSearchView.setOnQueryTextListener(this);
    }

    @Override
    public void setProgressVisible(boolean visible) {}

    @Override
    public void onEventLoaded(Event event) {
        songList.clear();
        songList.addAll(event.getSongs());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSongAdded(Song song) {
        songList.add(song);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewAttached(this);
        presenter.registerUpdateReceiver(this.getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onViewDetached();
        presenter.unregisterUpdateReceiver(this.getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(getActivity(), SearchActivity.class)
                .setAction(Intent.ACTION_SEARCH)
                .putExtra(SearchManager.QUERY, query);
        startActivityForResult(intent, RC_SEARCH_TRACK);
        MenuItemCompat.collapseActionView(addTrackMenuItem);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    Song toSong(Track track) {
        return new Song(track.getTitle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_SEARCH_TRACK : {
                if((resultCode == Activity.RESULT_OK)) {
                    Track track = data.getParcelableExtra(SearchActivity.EXTRA_TRACK);
                    presenter.adSong(toSong(track));
                }
                break;
            }
            default : {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
