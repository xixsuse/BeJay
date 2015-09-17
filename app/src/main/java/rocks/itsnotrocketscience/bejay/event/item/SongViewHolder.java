package rocks.itsnotrocketscience.bejay.event.item;

import android.view.View;
import android.widget.TextView;

import at.markushi.ui.CircleButton;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseViewHolder;

/**
 * Created by centralstation on 17/09/15.
 */
public class SongViewHolder extends BaseViewHolder {

    public static final int SONG_LIST_ITEM_LAYOUT = R.layout.card_song;
    TextView tvSongTitle;
    TextView tvLikesCount;
    CircleButton btnLikeSong;

    public SongViewHolder(View v) {
        super(v);
        tvLikesCount = (TextView)v.findViewById(R.id.tvLikesCount);
        tvSongTitle = (TextView)v.findViewById(R.id.tvSongTitle);
        btnLikeSong = (CircleButton)v.findViewById(R.id.btnLikeSong);
        btnLikeSong.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return SONG_LIST_ITEM_LAYOUT;
    }

}
