package rocks.itsnotrocketscience.bejay.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ModelViewHolder<M> extends RecyclerView.ViewHolder {
    public ModelViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setModel(M model);
}
