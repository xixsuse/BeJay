package rocks.itsnotrocketscience.bejay.search.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import rocks.itsnotrocketscience.bejay.music.model.Model;

public abstract class ModelViewHolder<M extends Model> extends RecyclerView.ViewHolder {
    ModelViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setModel(M model);
}
