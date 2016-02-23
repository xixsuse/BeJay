package rocks.itsnotrocketscience.bejay.search;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.search.model.Playlist;

public class PlaylistViewHolder extends ModelViewHolder<Playlist> {

    @Inject Resources resources;
    @Inject Picasso picasso;
    @Inject Transformation transformation;

    public TextView title;
    public TextView user;
    public ImageView picture;

    public PlaylistViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.user = (TextView) itemView.findViewById(R.id.user);
        this.picture = (ImageView) itemView.findViewById(R.id.picture);
    }

    @Override
    public void setModel(Playlist model) {
        this.title.setText(model.getTitle());
        this.user.setText(resources.getString(R.string.format_created_by_user, model.getUser()));
        this.picasso.load(model.getPicture()).transform(transformation).into(this.picture);
    }
}
