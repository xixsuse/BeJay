package rocks.itsnotrocketscience.bejay.event.single;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.RetrofitError;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.managers.RetrofitListeners;
import rocks.itsnotrocketscience.bejay.managers.RetrofitManager;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;

/**
 * A placeholder fragment containing a simple view.
 */
public class EventFragment extends BaseFragment implements RetrofitManager.EventListener, RetrofitListeners.SongAddedListener {

    @Bind(R.id.rvSongList)
    RecyclerView rvSongList;

    @Bind(R.id.etSongPicker)
    EditText etSongPicker;

    @Bind(R.id.ivSearch)
    ImageView ivSearch;

    SongListAdapter adapter;
    List<Song> songList;

    public EventFragment() {
        songList = new ArrayList<>();
    }

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSongList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvSongList.setLayoutManager(llm);
        adapter = new SongListAdapter(songList);
        adapter.setItemClickListener((view1, position) -> Log.d("yo", "yo"));

        rvSongList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, view);
        getEventFeed();
        return view;
    }

    private void getEventFeed() {
        RetrofitManager.get(getActivity()).getEventFeed(this, ((EventActivity) getActivity()).getIdFromBundle());
    }

    private void setViewItems(Event event) {
        this.songList.clear();
        this.songList.addAll(event.getSongs());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEventLoaded(Event event, RetrofitError error) {
        if (error == null) {
            setViewItems(event);
            ((EventActivity) getActivity()).setTitle(event.getTitle().toUpperCase());
        }
    }

    @OnClick(R.id.ivSearch)
    public void searchTrack() {
        //todo check song available
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Song Available!")
                .setContentText("Add Song To List?")
                .setCancelText("No Thanks!")
                .setConfirmText("Do It")
                .showCancelButton(true)
                .setCancelClickListener(SweetAlertDialog::cancel)
                .setConfirmClickListener(sDialog -> {
                    sDialog.cancel();
                    RetrofitManager.get(getActivity()).addSong(new Song(etSongPicker.getText().toString()), this);
                })
                .show();
    }

    @Override
    public void onSongAdded(Song song, RetrofitError error) {
        if (song != null) {
            getEventFeed();
        } else {
            Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
