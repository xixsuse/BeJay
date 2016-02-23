package rocks.itsnotrocketscience.bejay.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.search.model.Model;

public class ModelViewFactory {
    private LayoutInflater inflater;

    @Inject
    public ModelViewFactory(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public View create(ViewGroup parent, int type) {
        int layout = getLayoutResourceForType(type);
        if(layout != -1) {
            return inflater.inflate(layout, parent, false);
        }
        return null;
    }

    private int getLayoutResourceForType(int type) {
        switch (type) {
            case Model.TYPE_ALBUM : {
                return R.layout.list_item_album;
            }
            case Model.TYPE_ARTIST : {
                return R.layout.list_item_artist;
            }
            case Model.TYPE_TRACK : {
                return R.layout.list_item_track;
            }
            case Model.TYPE_PLAYLIST : {
                return R.layout.list_item_playlist;
            }

            default: {
                return -1;
            }
        }
    }
}
