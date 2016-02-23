package rocks.itsnotrocketscience.bejay.search;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.search.model.Track;
import rocks.itsnotrocketscience.bejay.view.CircleImageTransformation;

/**
 * Created by nemi on 20/02/2016.
 */


public class TrackViewHolder extends ModelViewHolder<Track> {

    @Inject Picasso picasso;
    @Inject Transformation transformation;
    @Inject Resources resources;

    public TextView title;
    public TextView artist;
    public ImageView cover;

    public TrackViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.artist = (TextView) itemView.findViewById(R.id.artist);
        this.cover = (ImageView) itemView.findViewById(R.id.cover);
    }

    @Override
    public void setModel(Track model) {
        this.title.setText(model.getTitle());
        setArtist(model.getArtist());
        loadImage(model.getCover());

    }

    private void loadImage(String cover) {
        if(this.cover != null) {
            if (!TextUtils.isEmpty(cover)) {
                this.picasso.load(cover).transform(transformation).into(this.cover);
            } else {
                this.cover.setImageBitmap(null);
            }
        }
    }

    private void setArtist(String name) {
        if(this.artist != null) {
            if(!TextUtils.isEmpty(name)) {
                this.artist.setVisibility(View.VISIBLE);
                this.artist.setText(resources.getString(R.string.format_by_artist, name));
            } else {
                this.artist.setVisibility(View.GONE);
            }
        }
    }

}
