package rocks.itsnotrocketscience.bejay.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import rocks.itsnotrocketscience.bejay.event.list.ItemClickListener;

/**
 * Created by centralstation on 17/09/15.
 *
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener clickListener;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View v) {
        if(clickListener!= null){
            clickListener.onClick(v, getLayoutPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public abstract int getLayoutId();
}
