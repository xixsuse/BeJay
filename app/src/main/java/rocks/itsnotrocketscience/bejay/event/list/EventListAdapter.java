package rocks.itsnotrocketscience.bejay.event.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rocks.itsnotrocketscience.bejay.base.BaseListAdapter;
import rocks.itsnotrocketscience.bejay.base.BaseViewHolder;
import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by centralstation on 12/09/15.
 *
 */
public class EventListAdapter extends BaseListAdapter<Event> {

    public EventListAdapter(List<Event> eventList) {
       super(eventList);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(EventViewHolder.EVENT_LIST_ITEM_LAYOUT, viewGroup, false);
        EventViewHolder holder = new EventViewHolder(itemView);
        holder.setClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Event event = items.get(position);
        ((EventViewHolder)holder).tvTitle.setText(event.getTitle());
    }

}
