package rocks.itsnotrocketscience.bejay.search;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.search.model.ArtistDetails;
import rocks.itsnotrocketscience.bejay.search.model.Model;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class ArtistDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int TYPE_SECTION_HEADER = Model.TYPE_FIRST;

    private ModelViewHolderFactory viewHolderFactory;
    private ModelViewFactory viewFactory;
    private LayoutInflater layoutInflater;
    private Resources resources;

    private ArtistDetails artistDetails;

    @Inject
    public ArtistDetailAdapter(ModelViewHolderFactory viewHolderFactory,
                               ModelViewFactory viewFactory,
                               LayoutInflater layoutInflater,
                               Resources resources) {
        this.viewHolderFactory = viewHolderFactory;
        this.viewFactory = viewFactory;
        this.layoutInflater = layoutInflater;
        this.resources = resources;
    }

    public void setArtistDetails(ArtistDetails artistDetails) {
        this.artistDetails = artistDetails;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = viewFactory.create(parent, viewType);
        if(view != null) {
            return viewHolderFactory.create(view, viewType);
        }

        view = layoutInflater.inflate(R.layout.list_item_section_header, parent, false);
        return new SectionHeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Model model = getModel(position);
        if(model != null) {
            ((ModelViewHolder)holder).setModel(model);
        }
        SectionHeaderViewHolder sectionHeaderViewHolder = (SectionHeaderViewHolder) holder;
        if(isTopTracksHeader(position)) {
            sectionHeaderViewHolder.setTitle(resources.getString(R.string.section_header_top_tracks));
        } else if(isDiscographyHeader(position)) {
            sectionHeaderViewHolder.setTitle(resources.getString(R.string.section_header_discography));
        }


    }

    private boolean isSectionHeader(int adapterPosition) {
        return isDiscographyHeader(adapterPosition) || isTopTracksHeader(adapterPosition);
    }

    private boolean isDiscographyHeader(int adapterPosition) {
        int topTracksCount = artistDetails.getTopTracks().size();
        int albumCount = artistDetails.getDiscography().size();

        if(albumCount > 0) {
            if(topTracksCount > 0) {
                return adapterPosition == topTracksCount;
            }

            return adapterPosition == 0;
        }

        return false;
    }

    private Model getModel(int adapterPosition) {
        int position = getTrackIndex(adapterPosition);
        if(position != NO_POSITION) {
            return artistDetails.getTopTracks().get(position);
        }

        position = getAlbumIndex(adapterPosition);
        if(position != NO_POSITION) {
            return artistDetails.getDiscography().get(position);
        }

        return null;
    }
    public <T> T getItem(int adapterPosition) {
        int position;
        if((position = getTrackIndex(adapterPosition)) != NO_POSITION) {
            return (T)artistDetails.getTopTracks().get(position);
        } else if((position = getAlbumIndex(adapterPosition)) != NO_POSITION) {
            return (T)artistDetails.getDiscography().get(position);
        }

        return null;
    }


    private int getTrackIndex(int adapterPosition) {
        int topTrackCount = artistDetails.getTopTracks().size();

        if(topTrackCount > 0 && adapterPosition > 0 && adapterPosition <= topTrackCount) {
            return adapterPosition - 1;
        }

        return NO_POSITION;
    }

    private int getAlbumIndex(int adapterPosition) {
        int topTrackCount = artistDetails.getTopTracks().size();
        int albumCount = artistDetails.getDiscography().size();

        if(albumCount > 0 ) {
            int offset = topTrackCount + 1;
            if(topTrackCount > 0) {
                offset++;
            }

            return adapterPosition - offset;
        }

        return NO_POSITION;
    }

    @Override
    public int getItemViewType(int position) {
        if(isSectionHeader(position)) {
            return TYPE_SECTION_HEADER;
        } else if(getTrackIndex(position) != NO_POSITION) {
            return Model.TYPE_TRACK;
        } else if(getAlbumIndex(position) != NO_POSITION) {
            return Model.TYPE_ALBUM;
        }

        return Model.TYPE_UNKNOWN;
    }

    private boolean isTopTracksHeader(int adapterPosition) {
        int topTracksCount = artistDetails.getTopTracks().size();
        return topTracksCount > 0 && adapterPosition == 0;
    }

    @Override
    public int getItemCount() {
        if(artistDetails != null) {
            int size = 0;
            int topTracksCount = artistDetails.getTopTracks().size();
            size += topTracksCount;
            if(topTracksCount > 0) {
                size++;
            }

            int albumCount = artistDetails.getDiscography().size();
            size+=albumCount;
            if(albumCount > 0) {
                size++;
            }

            return size;
        }

        return 0;
    }
}
