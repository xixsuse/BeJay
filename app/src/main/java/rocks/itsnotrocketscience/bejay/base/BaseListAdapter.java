package rocks.itsnotrocketscience.bejay.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import rocks.itsnotrocketscience.bejay.event.list.ItemClickListener;
import rocks.itsnotrocketscience.bejay.event.list.ViewHolderClickListener;

/**
 * Created by centralstation on 17/09/15.
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements ViewHolderClickListener {
    protected List<T> items;
    ItemClickListener<T> clickListener;

    public BaseListAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View view, int position) {
        clickListener.onClick(items.get(position));
    }

    public void setItemClickListener(ItemClickListener<T> click) {
        this.clickListener = click;
    }

}
