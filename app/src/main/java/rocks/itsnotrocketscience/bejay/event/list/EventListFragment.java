package rocks.itsnotrocketscience.bejay.event.list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
import rocks.itsnotrocketscience.bejay.api.GetEvents;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.models.Event;

public class EventListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.rvEventList)
    RecyclerView rvEventList;
    EventListAdapter adapter;
    List<Event> eventList;
    public static Fragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFeed();
        rvEventList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvEventList.setLayoutManager(llm);
        adapter = new EventListAdapter(eventList);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                Log.d("yo","yo");
            }
        });

        rvEventList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void getFeed() {
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConstants.API).build();
        GetEvents events = restAdapter.create(GetEvents.class);

        events.getFeed("events", new Callback<ArrayList<Event>>() {
            @Override
            public void success(ArrayList<Event> eventList, Response response) {
                setViewItems(eventList);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("yo", "yo");
            }
        });
    }

    private void setViewItems(ArrayList<Event> eventList) {
        this.eventList.clear();
        this.eventList.addAll(eventList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
