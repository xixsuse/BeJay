package rocks.itsnotrocketscience.bejay.search.view;

import android.content.res.Resources;
import android.text.TextUtils;
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
    @Inject public Picasso picasso;
    @Inject public Transformation transformation;

    private final TextView title;
    private final ImageView picture;

    public PlaylistViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.picture = (ImageView) itemView.findViewById(R.id.picture);
    }

    @Override
    public void setModel(Playlist model) {
        this.title.setText(model.getTitle());
        loadImage(model.getPicture());
    }

    private void loadImage(String picture) {
        if(this.picture != null) {
            if(!TextUtils.isEmpty(picture)) {
                this.picasso.load(picture).transform(transformation).into(this.picture);
            } else {
                this.picture.setImageBitmap(null);
            }
        }
    }
}
