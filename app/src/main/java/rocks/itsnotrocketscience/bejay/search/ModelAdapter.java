package rocks.itsnotrocketscience.bejay.search;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.search.model.Model;

/**
 * Created by nemi on 20/02/2016.
 */
public class ModelAdapter<M extends Model> extends RecyclerView.Adapter<ModelViewHolder<M>> {
    private final ViewHolderFactory<ModelViewHolder<M>> viewHolderFactory;
    private final ArrayList<M> tracks = new ArrayList<>();

    @Inject
    public ModelAdapter(ViewHolderFactory<ModelViewHolder<M>> viewHolderFactory) {
        this.viewHolderFactory = viewHolderFactory;
    }

    @Override
    public ModelViewHolder<M> onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewHolderFactory.create(parent);
    }

    @Override
    public void onBindViewHolder(ModelViewHolder<M> holder, int position) {
        M track = getModel(position);
        holder.setModel(track);
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public M getModel(int position) {
        return tracks.get(position);
    }

    public void reset() {
        int currentCount = getItemCount();
        tracks.clear();
        if(currentCount > 0) {
            notifyItemRangeRemoved(0, currentCount);
        }
    }

    public void addAll(List<M> tracks) {
        int currentCount = getItemCount();
        this.tracks.addAll(tracks);
        notifyItemRangeInserted(currentCount, tracks.size());
    }

    @Override
    public int getItemViewType(int position) {
        return tracks.get(position).getType();
    }
}
