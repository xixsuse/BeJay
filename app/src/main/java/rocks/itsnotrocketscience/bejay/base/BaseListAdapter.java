package rocks.itsnotrocketscience.bejay.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rocks.itsnotrocketscience.bejay.event.list.EventViewHolder;
import rocks.itsnotrocketscience.bejay.event.list.ItemClickListener;

/**
 * Created by centralstation on 17/09/15.
 *
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<EventViewHolder> implements ItemClickListener {
    protected List<T> items;
    ItemClickListener clickListener;

    public BaseListAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View view, int position) {
        clickListener.onClick(view,position);
    }

    public void setItemClickListener(ItemClickListener click){
        this.clickListener = click;
    }

}
