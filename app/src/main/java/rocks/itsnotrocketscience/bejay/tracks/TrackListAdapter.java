package rocks.itsnotrocketscience.bejay.tracks;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;

/**
 * Created by nemi on 20/02/2016.
 */
public class TrackListAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private final ArrayList<Track> tracks = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private final Resources resources;

    @Inject
    public TrackListAdapter(LayoutInflater layoutInflater, Resources resources) {
        this.layoutInflater = layoutInflater;
        this.resources = resources;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_track, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        Track track = getTrack(position);
        holder.setTitle(track.getTitle());
        holder.setArtist(resources.getString(R.string.format_by_artist, track.getArtist()));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public Track getTrack(int position) {
        return tracks.get(position);
    }

    public void reset() {
        int currentCount = getItemCount();
        tracks.clear();
        if(currentCount > 0) {
            notifyItemRangeRemoved(0, currentCount);
        }
    }

    public void addAll(List<Track> tracks) {
        int currentCount = getItemCount();
        this.tracks.addAll(tracks);
        notifyItemRangeInserted(currentCount, tracks.size());
    }
}
