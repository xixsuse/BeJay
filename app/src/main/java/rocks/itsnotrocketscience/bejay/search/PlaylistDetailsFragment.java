package rocks.itsnotrocketscience.bejay.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.music.model.Playlist;
import rocks.itsnotrocketscience.bejay.search.contract.MusicSearchContract;
import rocks.itsnotrocketscience.bejay.search.contract.PlaylistDetailsContract;
import rocks.itsnotrocketscience.bejay.view.ItemTouchHelper;

public class PlaylistDetailsFragment extends BaseFragment implements PlaylistDetailsContract.View, rocks.itsnotrocketscience.bejay.view.ItemTouchHelper.OnItemClickedListener{

    public static final String EXTRA_PLAYLIST_ID = "playlist_id";

    @Bind(R.id.search_result) RecyclerView tracks;
    @Bind(R.id.progress) ProgressBar progressIndicator;

    @Inject PlaylistDetailsContract.Presenter presenter;
    @Inject ModelAdapter modelAdapter;

    private LinearLayoutManager layoutManager;
    private ItemTouchHelper itemTouchHelper;
    private MusicSearchContract contract;
    private String playlistId;
    private Playlist playlist;

    public static PlaylistDetailsFragment newInstance(String playlistId) {
        PlaylistDetailsFragment fragment = new PlaylistDetailsFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_PLAYLIST_ID, playlistId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        playlistId = getArguments().getString(EXTRA_PLAYLIST_ID);
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
        tracks.setLayoutManager(layoutManager);
        tracks.setAdapter(modelAdapter);
        itemTouchHelper = new ItemTouchHelper(getContext());
        itemTouchHelper.setOnItemClickedListener(this);
        itemTouchHelper.setup(tracks);
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
    public void onItemClicked(RecyclerView recyclerView, int adapterPosition) {
        contract.onModelSelected(modelAdapter.getItem(adapterPosition));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onViewAttached(this);
        if(playlist == null) {
            presenter.loadPlaylist(playlistId);
        }
    }

    @Override
    public void onPlaylistLoaded(Playlist playlist) {
        this.playlist = playlist;
        modelAdapter.addAll(this.playlist.getTracks());
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
