package rocks.itsnotrocketscience.bejay.event.list;

import android.view.View;
import android.widget.TextView;

import at.markushi.ui.CircleButton;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseViewHolder;

/**
 * Created by centralstation on 12/09/15.
 */
public class EventViewHolder extends BaseViewHolder {

    public static final int EVENT_LIST_ITEM_LAYOUT = R.layout.card_event;
    TextView tvTitle;
    CircleButton btnCheckIn;

    public EventViewHolder(View v) {
        super(v);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        btnCheckIn = (CircleButton) v.findViewById(R.id.btnCheckIn);
        btnCheckIn.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return EVENT_LIST_ITEM_LAYOUT;
    }

}
