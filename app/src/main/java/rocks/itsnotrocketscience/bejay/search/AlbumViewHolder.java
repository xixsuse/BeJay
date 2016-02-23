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
import rocks.itsnotrocketscience.bejay.search.model.Album;
import rocks.itsnotrocketscience.bejay.view.CircleImageTransformation;

public class AlbumViewHolder extends ModelViewHolder<Album> {
    private Picasso picasso;
    private Resources resources;
    private Transformation transformation;

    private TextView title;
    private TextView artist;
    private ImageView cover;


    public static class Factory extends ViewHolderFactory<ModelViewHolder<Album>> {
        private static final Transformation TRANSFORMATION = new CircleImageTransformation();
        private Picasso picasso;
        private Resources resources;

        @Inject
        public Factory(LayoutInflater layoutInflater, Picasso picasso, Resources resources) {
            super(R.layout.list_item_album, layoutInflater);
            this.picasso = picasso;
            this.resources = resources;
        }

        @Override
        protected ModelViewHolder<Album> create(View view) {
            return new AlbumViewHolder(picasso, TRANSFORMATION, resources, view);
        }
    }
    public AlbumViewHolder(Picasso picasso, Transformation transformation, Resources resources, View itemView) {
        super(itemView);
        this.picasso = picasso;
        this.transformation = transformation;
        this.resources = resources;
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.artist = (TextView) itemView.findViewById(R.id.artist);
        this.cover = (ImageView) itemView.findViewById(R.id.cover);
    }

    @Override
    public void setModel(Album model) {
        this.title.setText(model.getTitle());
        this.artist.setText(resources.getString(R.string.format_by_artist, model.getArtist()));
        this.picasso.load(model.getCover()).transform(transformation).into(this.cover);
    }
}
