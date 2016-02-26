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
import rocks.itsnotrocketscience.bejay.music.model.Album;
import rocks.itsnotrocketscience.bejay.search.contract.AlbumDetailsContract;
import rocks.itsnotrocketscience.bejay.search.contract.MusicSearchContract;
import rocks.itsnotrocketscience.bejay.view.ItemTouchHelper;

public class AlbumDetailsFragment extends BaseFragment implements AlbumDetailsContract.View, rocks.itsnotrocketscience.bejay.view.ItemTouchHelper.OnItemClickedListener{

    public static final String EXTRA_ALBUM_ID = "album_id";

    @Bind(R.id.search_result) RecyclerView tracks;
    @Bind(R.id.progress) ProgressBar progressIndicator;

    @Inject AlbumDetailsContract.Presenter presenter;
    @Inject ModelAdapter modelAdapter;

    private LinearLayoutManager layoutManager;
    private ItemTouchHelper itemTouchHelper;
    private MusicSearchContract contract;
    private String albumId;
    private Album album;

    public static AlbumDetailsFragment newInstance(String albumId) {
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_ALBUM_ID, albumId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        albumId = getArguments().getString(EXTRA_ALBUM_ID);
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
        contract.onModelSelected(modelAdapter.getModel(adapterPosition));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onViewAttached(this);
        if(album == null) {
            presenter.loadAlbum(albumId);
        }
    }

    @Override
    public void onAlbumLoaded(Album album) {
        this.album = album;
        modelAdapter.addAll(album.getTracks());
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
