package rocks.itsnotrocketscience.bejay.search;

import android.content.res.Resources;
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

    private Picasso picasso;
    private Transformation transformation;
    private Resources resources;
    public TextView title;
    public TextView artist;
    public ImageView cover;

    public static class Factory extends ViewHolderFactory<ModelViewHolder<Track>> {
        private static final Transformation TRANSFORMATION = new CircleImageTransformation();
        private final Picasso picasso;
        private final Resources resources;

        @Inject
        public Factory(LayoutInflater layoutInflater, Picasso picasso, Resources resources) {
            super(R.layout.list_item_track, layoutInflater);
            this.picasso = picasso;
            this.resources = resources;
        }

        @Override
        protected ModelViewHolder<Track> create(View view) {
            return new TrackViewHolder(picasso, TRANSFORMATION, resources, view);
        }
    }

    public TrackViewHolder(Picasso picasso, Transformation transformation, Resources resources, View itemView) {
        super(itemView);
        this.picasso = picasso;
        this.transformation = transformation;
        this.resources = resources;
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.artist = (TextView) itemView.findViewById(R.id.artist);
        this.cover = (ImageView) itemView.findViewById(R.id.cover);
    }

    @Override
    public void setModel(Track model) {
        this.title.setText(model.getTitle());
        this.artist.setText(resources.getString(R.string.format_by_artist, model.getArtist()));
        this.picasso.load(model.getCover()).transform(transformation).into(cover);
    }
}
