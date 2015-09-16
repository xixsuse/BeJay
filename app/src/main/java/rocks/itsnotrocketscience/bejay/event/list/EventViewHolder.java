package rocks.itsnotrocketscience.bejay.event.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rocks.itsnotrocketscience.bejay.R;

/**
 * Created by centralstation on 12/09/15.
 */
public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public static final int EVENT_LIST_ITEM_LAYOUT = R.layout.card_event;
    TextView tvTitle;
    private ItemClickListener clickListener;
    public EventViewHolder(View v) {
        super(v);
        tvTitle = (TextView)v.findViewById(R.id.tvTitle);
        v.setOnClickListener(this);

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
}
