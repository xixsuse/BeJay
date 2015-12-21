package rocks.itsnotrocketscience.bejay.event.single;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rocks.itsnotrocketscience.bejay.base.BaseListAdapter;
import rocks.itsnotrocketscience.bejay.base.BaseViewHolder;
import rocks.itsnotrocketscience.bejay.models.Song;

/**
 * Created by centralstation on 17/09/15.
 */
public class SongListAdapter extends BaseListAdapter<Song> {

    public SongListAdapter(List<Song> eventList) {
        super(eventList);
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(SongViewHolder.SONG_LIST_ITEM_LAYOUT, viewGroup, false);
        SongViewHolder holder = new SongViewHolder(itemView);
        holder.setClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Song song = items.get(position);
        ((SongViewHolder) holder).tvSongTitle.setText(song.getTitle());
        ((SongViewHolder) holder).tvLikesCount.setText(song.getCountStr());
    }

}

