package rocks.itsnotrocketscience.bejay.event.single;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.ApiConstants;
import rocks.itsnotrocketscience.bejay.api.retrofit.GetEvent;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;

/**
 * A placeholder fragment containing a simple view.
 */
public class EventFragment extends BaseFragment {

    @Bind(R.id.rvSongList)
    RecyclerView rvSongList;
    SongListAdapter adapter;
    List<Song> songList;

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

    public EventFragment() {
        songList = new ArrayList<>();
    }

    public static EventFragment newInstance(){
        return new EventFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, view);
        if(getAppApplication().getAccountManager().isCheckedIn()){
            getFeed((getAppApplication().getAccountManager().getCheckedInEventPk()));
        }
        else{
            getFeed(((EventActivity) getActivity()).getIdFromBundle());
        }

        return view;
    }


    private void getFeed(int url) {
        RestAdapter restAdapter = new RestAdapter.Builder().setRequestInterceptor(getAppApplication().getAccountManager().getAuthTokenInterceptor()).setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConstants.EVENTS_API).build();
        GetEvent events = restAdapter.create(GetEvent.class);

        events.getFeed(url, new Callback<Event>() {
            @Override
            public void success(Event eventList, Response response) {
                setViewItems(eventList);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("yo", "yo");
            }
        });
    }

    private void setViewItems(Event event) {
        this.songList.clear();
        this.songList.addAll(event.getSongs());
        adapter.notifyDataSetChanged();
    }
}
