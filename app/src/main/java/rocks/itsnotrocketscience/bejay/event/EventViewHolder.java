package rocks.itsnotrocketscience.bejay.event;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rocks.itsnotrocketscience.bejay.R;

/**
 * Created by centralstation on 12/09/15.
 */
public class EventViewHolder  extends RecyclerView.ViewHolder{
    TextView tvTitle;
    public EventViewHolder(View v) {
        super(v);
        tvTitle = (TextView)v.findViewById(R.id.tvTitle);

    }
}
