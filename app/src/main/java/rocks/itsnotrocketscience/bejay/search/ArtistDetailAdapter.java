package rocks.itsnotrocketscience.bejay.search;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.music.model.ArtistDetails;
import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.search.view.ModelViewHolder;
import rocks.itsnotrocketscience.bejay.search.view.ModelViewHolderFactory;
import rocks.itsnotrocketscience.bejay.search.view.SectionHeaderViewHolder;

import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static rocks.itsnotrocketscience.bejay.music.model.Model.TYPE_ALBUM;
import static rocks.itsnotrocketscience.bejay.music.model.Model.TYPE_TRACK;

public class ArtistDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SECTION_HEADER = Model.TYPE_FIRST;

    private ModelViewHolderFactory viewHolderFactory;
    private LayoutInflater layoutInflater;
    private Resources resources;

    private ArtistDetails artistDetails;

    private int topTracksHeaderIndex;
    private int discographyHeaderIndex;
    private int itemCount;

    @Inject
    public ArtistDetailAdapter(ModelViewHolderFactory viewHolderFactory,
                               LayoutInflater layoutInflater,
                               Resources resources) {
        this.viewHolderFactory = viewHolderFactory;
        this.layoutInflater = layoutInflater;
        this.resources = resources;
    }

    public void setArtistDetails(ArtistDetails artistDetails) {
        if(this.artistDetails != artistDetails) {
            this.artistDetails = artistDetails;
            updateIndices();
            notifyDataSetChanged();
        }
    }

    private void updateIndices() {
        topTracksHeaderIndex = NO_POSITION;
        discographyHeaderIndex = NO_POSITION;
        itemCount = 0;

        if(artistDetails != null) {
            int offset = 0;
            int topTracksCount = getTopTracksCount();
            int albumsCount = getAlbumsCount();

            if(topTracksCount > 0) {
                topTracksHeaderIndex = offset;
                offset += topTracksCount + 1;
            }

            if (albumsCount > 0) {
                discographyHeaderIndex = offset;
                offset += albumsCount + 1;
            }

            itemCount = offset;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case Model.TYPE_TRACK : {
                View view = layoutInflater.inflate(R.layout.list_item_artist_detail_track, parent, false);
                return viewHolderFactory.create(view, viewType);
            }
            case Model.TYPE_ALBUM : {
                View view = layoutInflater.inflate(R.layout.list_item_artist_detail_album, parent, false);
                return viewHolderFactory.create(view, viewType);
            }
            case TYPE_SECTION_HEADER : {
                View view = layoutInflater.inflate(R.layout.list_item_section_header, parent, false);
                return new SectionHeaderViewHolder(view);
            }
            default: {
                return null;
            }
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Model model = getItem(position);
        if(model != null) {
            ((ModelViewHolder)holder).setModel(model);
        } else {
            SectionHeaderViewHolder sectionHeaderViewHolder = (SectionHeaderViewHolder) holder;
            if (position == topTracksHeaderIndex) {
                sectionHeaderViewHolder.setTitle(resources.getString(R.string.section_header_top_tracks));
            } else if (position == discographyHeaderIndex) {
                sectionHeaderViewHolder.setTitle(resources.getString(R.string.section_header_discography));
            }
        }
    }

    public <T> T getItem(int position) {
        if(position > topTracksHeaderIndex && position < discographyHeaderIndex) {
            return (T)artistDetails.getTopTracks().get(position-(topTracksHeaderIndex + 1));
        } else if(position > discographyHeaderIndex && position < getItemCount()) {
            return (T)artistDetails.getDiscography().get(position - (discographyHeaderIndex + 1));
        }

        return null;
    }


    @Override
    public int getItemViewType(int position) {
        if(position == topTracksHeaderIndex || position == discographyHeaderIndex) {
            return TYPE_SECTION_HEADER;
        } else if(position > topTracksHeaderIndex && position < discographyHeaderIndex) {
            return TYPE_TRACK;
        } else if(position > discographyHeaderIndex && position < getItemCount()) {
            return TYPE_ALBUM;
        }

        return Model.TYPE_UNKNOWN;
    }



    private int getTopTracksCount() {
        if(artistDetails != null && artistDetails.getTopTracks() != null) {
            return artistDetails.getTopTracks().size();
        }

        return 0;
    }

    private int getAlbumsCount() {
        if(artistDetails != null && artistDetails.getDiscography() != null) {
            return artistDetails.getDiscography().size();
        }

        return 0;
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }
}
