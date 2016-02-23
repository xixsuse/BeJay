package rocks.itsnotrocketscience.bejay.search;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.view.CircleImageTransformation;

public class ArtistViewHolder extends ModelViewHolder<Artist> {
    private Picasso picasso;
    private Transformation transformation;
    private TextView name;
    private ImageView picture;

    public static class Factory extends ViewHolderFactory<ModelViewHolder<Artist>> {
        private static final Transformation TRANSFORMATION = new CircleImageTransformation();

        private Picasso picasso;
        @Inject
        public Factory(Picasso picasso, LayoutInflater layoutInflater) {
            super(R.layout.list_item_artist, layoutInflater);
            this.picasso = picasso;
        }

        @Override
        protected ModelViewHolder<Artist> create(View view) {
            return new ArtistViewHolder(picasso, TRANSFORMATION, view);
        }
    }

    public ArtistViewHolder(Picasso picasso, Transformation transformation, View itemView) {
        super(itemView);
        this.picasso = picasso;
        this.transformation = transformation;
        this.name = (TextView) itemView.findViewById(R.id.name);
        this.picture = (ImageView) itemView.findViewById(R.id.picture);
    }

    @Override
    public void setModel(Artist artist) {
        name.setText(artist.getName());
        picasso.load(artist.getPicture()).transform(transformation).into(picture);
    }
}
