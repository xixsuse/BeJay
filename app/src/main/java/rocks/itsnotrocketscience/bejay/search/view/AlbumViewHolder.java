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
import rocks.itsnotrocketscience.bejay.music.model.Album;
import rocks.itsnotrocketscience.bejay.music.model.Artist;

public class AlbumViewHolder extends ModelViewHolder<Album> {
    @Inject public Picasso picasso;
    @Inject public Resources resources;
    @Inject public Transformation transformation;

    private final TextView title;
    private final TextView artist;
    private final ImageView cover;


    public AlbumViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.artist = (TextView) itemView.findViewById(R.id.artist);
        this.cover = (ImageView) itemView.findViewById(R.id.cover);
    }

    @Override
    public void setModel(Album model) {
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

    private void setArtist(Artist artist) {
        if(this.artist != null) {
            if(artist != null) {
                this.artist.setVisibility(View.VISIBLE);
                this.artist.setText(resources.getString(R.string.format_by_artist, artist.getName()));
            } else {
                this.artist.setVisibility(View.GONE);
            }
        }
    }
}
