package rocks.itsnotrocketscience.bejay.tracks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rocks.itsnotrocketscience.bejay.R;

/**
 * Created by nemi on 20/02/2016.
 */
public class TrackViewHolder extends RecyclerView.ViewHolder {

    public TextView title;

    public TrackViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void setTrack(Track track) {
        title.setText(track.getTitle());
    }
}
