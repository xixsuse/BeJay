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
import rocks.itsnotrocketscience.bejay.music.model.Track;

/**
 * Created by nemi on 20/02/2016.
 */


public class TrackViewHolder extends ModelViewHolder<Track> {

    @Inject public Picasso picasso;
    @Inject public Transformation transformation;
    @Inject public Resources resources;

    private final TextView title;
    private final TextView artist;
    private final ImageView cover;

    public TrackViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title);
        this.artist = (TextView) itemView.findViewById(R.id.artist);
        this.cover = (ImageView) itemView.findViewById(R.id.cover);
    }

    @Override
    public void setModel(Track model) {
        Album album = model.getAlbum();
        Artist artist = model.getArtist();

        title.setText(model.getTitle());

        setArtist(artist);

        String cover = null;
        if(album != null) {
            cover = album.getCover();
        }

        if(cover == null && artist != null) {
            cover = artist.getPicture();
        }

        loadImage(cover);

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
        if(this.artist != null ) {
            if(artist != null) {
                this.artist.setVisibility(View.VISIBLE);
                this.artist.setText(resources.getString(R.string.format_by_artist, artist.getName()));
            } else {
                this.artist.setVisibility(View.GONE);
            }
        }
    }
}
