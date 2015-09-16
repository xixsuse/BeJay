package rocks.itsnotrocketscience.bejay.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by centralstation on 12/09/15.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventViewHolder> implements ItemClickListener{
    private List<Event> eventList;
    ItemClickListener clickListener;

    public EventListAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }


    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(EventViewHolder.EVENT_LIST_ITEM_LAYOUT, viewGroup, false);
        EventViewHolder holder = new EventViewHolder(itemView);
        holder.setClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.tvTitle.setText(event.getTitle());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @Override
    public void onClick(View view, int position) {
        clickListener.onClick(view,position);
    }

    public void setItemClickListener(ItemClickListener click){
        this.clickListener = click;
    }


}
