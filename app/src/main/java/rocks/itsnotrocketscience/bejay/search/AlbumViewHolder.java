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
    @Inject Picasso picasso;
    @Inject Resources resources;
    @Inject Transformation transformation;

    private TextView title;
    private TextView artist;
    private ImageView cover;


    public AlbumViewHolder(View itemView) {
        super(itemView);
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
