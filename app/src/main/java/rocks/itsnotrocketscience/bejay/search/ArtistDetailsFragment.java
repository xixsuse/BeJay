package rocks.itsnotrocketscience.bejay.search;

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
import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.base.InjectedFragment;
import rocks.itsnotrocketscience.bejay.search.di.SearchComponent;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.search.model.ArtistDetails;

public class ArtistDetailsFragment extends InjectedFragment<SearchComponent> implements ArtistDetailsContract.View {

    public static final String EXTRA_ARTIST = "artist";

    @Inject ArtistDetailAdapter artistDetailAdapter;
    @Inject ArtistDetailsPresenter presenter;

    @Bind(R.id.search_result) RecyclerView artistDetailsView;
    Artist artist;


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
        presenter.onViewAttached(this);
        presenter.loadArtist(artist);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDetached();
    }

    @Override
    public void onLoaded(ArtistDetails artistDetails) {
        artistDetailAdapter.setArtistDetails(artistDetails);
    }

    @Override
    public SearchComponent getComponent() {
        InjectedActivity<SearchComponent> searchActivity = (InjectedActivity<SearchComponent>) getActivity();
        return searchActivity.getComponent();
    }
}
