package rocks.itsnotrocketscience.bejay.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.models.Event;

/**
 * Created by centralstation on 12/09/15.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private List<Event> eventList;

    public EventListAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }


    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_event, viewGroup, false);

        return new EventViewHolder(itemView);
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

}
