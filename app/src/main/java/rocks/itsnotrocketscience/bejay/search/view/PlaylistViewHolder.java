package rocks.itsnotrocketscience.bejay.search.view;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.music.model.Playlist;

public class PlaylistViewHolder extends ModelViewHolder<Playlist> {

    @Inject Resources resources;
    @Inject Picasso picasso;
    @Inject Transformation transformation;

    public TextView title;
    public ImageView picture;

    public PlaylistViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.picture = (ImageView) itemView.findViewById(R.id.picture);
    }

    @Override
    public void setModel(Playlist model) {
        this.title.setText(model.getTitle());
        this.picasso.load(model.getPicture()).transform(transformation).into(this.picture);
    }
}
