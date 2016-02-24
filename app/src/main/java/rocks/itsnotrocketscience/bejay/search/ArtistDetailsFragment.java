package rocks.itsnotrocketscience.bejay.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.music.model.Artist;
import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.search.contract.ArtistDetailsContract;
import rocks.itsnotrocketscience.bejay.search.contract.MusicSearchContract;
import rocks.itsnotrocketscience.bejay.search.presenter.ArtistDetailsPresenter;
import rocks.itsnotrocketscience.bejay.view.ItemTouchHelper;

public class ArtistDetailsFragment extends BaseFragment implements ArtistDetailsContract.View, ItemTouchHelper.OnItemClickedListener {

    public static final String EXTRA_ARTIST = "artist";

    @Inject ArtistDetailAdapter artistDetailAdapter;
    @Inject ArtistDetailsPresenter presenter;
    @Bind(R.id.search_result) RecyclerView artistDetailsView;

    private Artist artist;
    private ArtistDetailsContract.ArtistDetails artistDetails;
    private MusicSearchContract contract;
    private ItemTouchHelper itemTouchHelper;

    public static ArtistDetailsFragment newInstance(Artist artist) {
        ArtistDetailsFragment fragment = new ArtistDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_ARTIST, artist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        artist = getArguments().getParcelable(EXTRA_ARTIST);

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        artistDetailsView.setAdapter(artistDetailAdapter);
        artistDetailsView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemTouchHelper = new ItemTouchHelper(getContext());
        itemTouchHelper.setOnItemClickedListener(this);
        itemTouchHelper.setup(artistDetailsView);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onViewAttached(this);
        if(artistDetails == null) {
            presenter.loadArtistDetails(artist.getId());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onViewDetached();
    }

    @Override
    public void onLoaded(ArtistDetailsContract.ArtistDetails artistDetails) {
        this.artistDetails = artistDetails;
        artistDetailAdapter.setArtistDetails(artistDetails);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int adapterPosition) {
        Model model = artistDetailAdapter.getItem(adapterPosition);
        if(model != null) {
            contract.onModelSelected(model);
        }
    }
}
