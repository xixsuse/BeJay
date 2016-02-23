package rocks.itsnotrocketscience.bejay.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import rocks.itsnotrocketscience.bejay.search.model.Model;

public abstract class ModelViewHolder<M extends Model> extends RecyclerView.ViewHolder {
    public ModelViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setModel(M model);
}
