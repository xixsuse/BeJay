package rocks.itsnotrocketscience.bejay.search;

import android.view.View;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.search.model.Model;

public class ModelViewHolderFactory {
    private ActivityComponent injector;

    @Inject
    public ModelViewHolderFactory(ActivityComponent injector) {
        this.injector = injector;
    }

    public ModelViewHolder create(View view, int type) {
        ModelViewHolder viewHolder;
        switch (type) {
            case Model.TYPE_ALBUM : {
                AlbumViewHolder albumViewHolder = new AlbumViewHolder(view);
                injector.inject(albumViewHolder);
                viewHolder = albumViewHolder;
                break;
            }
            case Model.TYPE_ARTIST : {
                ArtistViewHolder artistViewHolder = new ArtistViewHolder(view);
                injector.inject(artistViewHolder);
                viewHolder = artistViewHolder;
                break;
            }
            case Model.TYPE_TRACK : {
                TrackViewHolder trackViewHolder = new TrackViewHolder(view);
                injector.inject(trackViewHolder);
                viewHolder = trackViewHolder;
                break;
            }
            case Model.TYPE_PLAYLIST : {
                PlaylistViewHolder playlistViewHolder = new PlaylistViewHolder(view);
                injector.inject(playlistViewHolder);
                viewHolder = playlistViewHolder;
                break;
            }
            default: {
                viewHolder = null;
            }

        }
        return viewHolder;
    }
}
