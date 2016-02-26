package rocks.itsnotrocketscience.bejay.search;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.music.model.Album;
import rocks.itsnotrocketscience.bejay.music.model.Artist;
import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.music.model.Playlist;
import rocks.itsnotrocketscience.bejay.music.model.Track;
import rocks.itsnotrocketscience.bejay.search.contract.TopLevelSearchContract;
import rocks.itsnotrocketscience.bejay.search.view.ModelViewFactory;
import rocks.itsnotrocketscience.bejay.search.view.ModelViewHolder;
import rocks.itsnotrocketscience.bejay.search.view.ModelViewHolderFactory;
import rocks.itsnotrocketscience.bejay.search.view.SectionHeaderViewHolder;
import rocks.itsnotrocketscience.bejay.search.view.ShowMoreViewHolder;
import rx.functions.Func1;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class TopLevelSearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_SECTION_HEADER = Model.TYPE_FIRST;
    public static final int TYPE_SHOW_MORE = TYPE_SECTION_HEADER + 1;

    private final ModelViewFactory modelViewFactory;
    private final ModelViewHolderFactory modelViewHolderFactory;
    private final LayoutInflater layoutInflater;
    private final Resources resources;

    private TopLevelSearchContract.SearchResult searchResult;
    // for fast lookup of view types
    private ArrayList<Integer> viewTypeMap;
    private SparseArray<Integer> headerTitle;
    private SparseArray<Func1<Integer, Model>> modelLookup;
    private SparseArray<Integer> showMoreType;
    private int itemCount;

    @Inject
    public TopLevelSearchResultAdapter(ModelViewFactory modelViewFactory,
                                       ModelViewHolderFactory modelViewHolderFactory,
                                       LayoutInflater layoutInflater,
                                       Resources resources) {
        this.modelViewFactory = modelViewFactory;
        this.modelViewHolderFactory = modelViewHolderFactory;
        this.layoutInflater = layoutInflater;
        this.resources = resources;
    }

    public void setSearchResult(TopLevelSearchContract.SearchResult searchResult) {
        if(this.searchResult != searchResult) {
            this.searchResult = searchResult;
            updateIndices();
            notifyDataSetChanged();
        }
    }

    public void reset() {
        setSearchResult(null);
    }

    private Func1<Integer, Model> newModelLookup(final int offset, List<? extends Model> models) {
        return adapterPosition -> models.get(adapterPosition - offset);

    }

    private void updateIndices() {
        // reset state before update
        itemCount = 0;
        viewTypeMap = null;
        modelLookup = null;
        headerTitle = null;
        showMoreType = null;

        if(this.searchResult != null) {
            viewTypeMap = new ArrayList<>();
            modelLookup = new SparseArray<>();
            headerTitle = new SparseArray<>();
            showMoreType = new SparseArray<>();

            int offset = 0;
            List<Track> tracks = searchResult.getTracks();
            if(tracks != null || tracks.size() > 0) {
                setupViewTypeMap(Model.TYPE_TRACK, offset, offset + tracks.size() + 1);
                modelLookup.put(Model.TYPE_TRACK, newModelLookup(offset + 1, tracks));
                headerTitle.put(offset, R.string.section_header_tracks);
                showMoreType.put(offset + tracks.size() + 1, Model.TYPE_TRACK);
                offset += tracks.size() + 2;
            }

            List<Album> albums = searchResult.getAlbums();
            if(albums != null && albums.size() > 0) {
                setupViewTypeMap(Model.TYPE_ALBUM, offset, offset + albums.size() + 1);
                modelLookup.put(Model.TYPE_ALBUM, newModelLookup(offset + 1, albums));
                headerTitle.put(offset, R.string.section_header_albums);
                showMoreType.put(offset + tracks.size() + 1, Model.TYPE_ALBUM);
                offset += albums.size() + 2;
            }

            List<Playlist> playlists = searchResult.getPlaylists();
            if(playlists != null && playlists.size() > 0) {
                setupViewTypeMap(Model.TYPE_PLAYLIST, offset, offset + playlists.size() + 1);
                modelLookup.put(Model.TYPE_PLAYLIST, newModelLookup(offset + 1, playlists));
                headerTitle.put(offset, R.string.section_header_playlists);
                showMoreType.put(offset + tracks.size() + 1, Model.TYPE_PLAYLIST);
                offset += playlists.size() + 2;
            }

            List<Artist> artists = searchResult.getArtists();
            if(artists != null && artists.size() > 0) {
                setupViewTypeMap(Model.TYPE_ARTIST, offset, offset + artists.size() + 1);
                modelLookup.put(Model.TYPE_ARTIST, newModelLookup(offset + 1, artists));
                headerTitle.put(offset, R.string.section_header_artists);
                showMoreType.put(offset + tracks.size() + 1, Model.TYPE_ARTIST);
                offset += artists.size() + 2;
            }

            itemCount = offset;
        }
    }

    private void setupViewTypeMap(@Model.Type int viewType, int lb, int hb) {
        viewTypeMap.add(lb, TYPE_SECTION_HEADER);
        for(int i = lb+1; i < hb; i++) {
            viewTypeMap.add(i, viewType);
        }
        viewTypeMap.add(hb, TYPE_SHOW_MORE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_SECTION_HEADER : {
                View view = layoutInflater.inflate(R.layout.list_item_section_header, parent, false);
                SectionHeaderViewHolder viewHolder = new SectionHeaderViewHolder(view);
                return viewHolder;
            }
            case TYPE_SHOW_MORE : {
                View view = layoutInflater.inflate(R.layout.list_item_show_more, parent, false);
                return new ShowMoreViewHolder(view);
            }
            default: {
                View view = modelViewFactory.create(parent, viewType);
                return modelViewHolderFactory.create(view, viewType);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_SECTION_HEADER : {
                ((SectionHeaderViewHolder)holder).setTitle(getHeaderTitle(position));
                break;
            }
            case TYPE_SHOW_MORE : break;
            default : {
                ((ModelViewHolder)holder).setModel(getModel(position));
            }
        }
    }

    public Model getModel(int position) {
        return getModel(position, modelLookup.get(getItemViewType(position)));
    }

    @Model.Type public int getShowMoreType(int position) {
        return showMoreType.get(position, Model.TYPE_UNKNOWN);
    }

    private Model getModel(int position, Func1<Integer, Model> modelLookup) {
        if(modelLookup != null) {
            return modelLookup.call(position);
        }

        return null;
    }

    private String getHeaderTitle(int position) {
        Integer titleResId = headerTitle.get(position);
        if(titleResId != null) {
            return resources.getString(titleResId);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypeMap.get(position);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }
}
