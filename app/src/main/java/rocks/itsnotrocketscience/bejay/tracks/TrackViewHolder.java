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
    public TextView artist;

    public TrackViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        artist = (TextView) itemView.findViewById(R.id.artist);
    }

    public void setTitle(CharSequence title) {
        this.title.setText(title);
    }

    public void setArtist(CharSequence artist) {
        this.artist.setText(artist);
    }
}
