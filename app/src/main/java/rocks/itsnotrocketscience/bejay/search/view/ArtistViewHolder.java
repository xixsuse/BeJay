package rocks.itsnotrocketscience.bejay.search.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.music.model.Artist;

public class ArtistViewHolder extends ModelViewHolder<Artist> {
    @Inject public Picasso picasso;
    @Inject public Transformation transformation;

    private final TextView name;
    private final ImageView picture;

    public ArtistViewHolder(View itemView) {
        super(itemView);
        this.name = (TextView) itemView.findViewById(R.id.name);
        this.picture = (ImageView) itemView.findViewById(R.id.picture);
    }

    @Override
    public void setModel(Artist artist) {
        name.setText(artist.getName());
        picasso.load(artist.getPicture()).transform(transformation).into(picture);
    }
}
