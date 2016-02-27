package rocks.itsnotrocketscience.bejay.search;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.search.view.ModelViewFactory;
import rocks.itsnotrocketscience.bejay.search.view.ModelViewHolder;
import rocks.itsnotrocketscience.bejay.search.view.ModelViewHolderFactory;

/**
 * Created by nemi on 20/02/2016.
 */
public class ModelAdapter extends BaseAdapter<ModelViewHolder, Model> {
    private ModelViewFactory viewFactory;
    private ModelViewHolderFactory viewHolderFactory;

    private ArrayList<Model> models = new ArrayList<>();

    @Inject
    public ModelAdapter(ModelViewFactory viewFactory, ModelViewHolderFactory viewHolderFactory) {
        this.viewFactory = viewFactory;
        this.viewHolderFactory = viewHolderFactory;
    }

    @Override
    public ModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = viewFactory.create(parent, viewType);
        if(view != null) {
            return viewHolderFactory.create(view, viewType);
        }

        throw new IllegalArgumentException("viewType("+viewType+") not supported by adapter");
    }

    @Override
    public void onBindViewHolder(ModelViewHolder holder, int position) {
        Model track = getItem(position);
        holder.setModel(track);
    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public Model getItem(int position) {
        return models.get(position);
    }

    public void reset() {
        int currentCount = getItemCount();
        models.clear();
        if(currentCount > 0) {
            notifyItemRangeRemoved(0, currentCount);
        }
    }

    public void addAll(List<? extends Model> models) {
        int currentCount = getItemCount();
        this.models.addAll(models);
        notifyItemRangeInserted(currentCount, models.size());
    }

    @Override
    public int getItemViewType(int position) {
        return models.get(position).getType();
    }
}
