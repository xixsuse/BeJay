package rocks.itsnotrocketscience.bejay.event.list;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import rocks.itsnotrocketscience.bejay.managers.LaunchManager;
import rocks.itsnotrocketscience.bejay.managers.RetrofitManager;
import rocks.itsnotrocketscience.bejay.models.Event;

public class EventListFragment extends BaseFragment implements ItemClickListener, RetrofitManager.EventListListener, RetrofitManager.CheckoutListener {

    @Bind(R.id.rvEventList)
    RecyclerView rvEventList;
    EventListAdapter adapter;
    @Bind(R.id.rlError)
    RelativeLayout rlError;
    @Bind(R.id.btnRetry)
    Button btnRetry;
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
        adapter.setItemClickListener(this);
        rvEventList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnRetry)
    public void getFeed() {
        RetrofitManager.get(getActivity()).getEventListFeed(this, rlError);
    }

    private void setViewItems(ArrayList<Event> eventList) {
        this.eventList.clear();
        this.eventList.addAll(eventList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {
        if (!getAppApplication().getAccountManager().isCheckedIn()) {
            RetrofitManager.get(getActivity()).checkinUser(eventList.get(position).getId(), getActivity());
        } else {
            displayAlert(eventList.get(position).getId());
        }
    }

    @Override
    public void onEventFeedLoaded(ArrayList<Event> list, RetrofitError error) {
        if (list != null) {
            setViewItems(list);
        } else {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCheckedOut(int id, RetrofitError error) {
        if (error != null) {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            RetrofitManager.get(getActivity()).checkinUser(id, getActivity());
        }
    }

    private void displayAlert(int id) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Already checked in to another event!")
                .setContentText("Go to checked in event or check in to this one?")
                .setCancelText("Current Event")
                .setConfirmText("This one")
                .showCancelButton(true)
                .setCancelClickListener(sDialog -> {
                    sDialog.cancel();
                    LaunchManager.launchEvent(getAppApplication().getAccountManager().getCheckedInEventPk(), getActivity());

                })
                .setConfirmClickListener(sDialog -> {
                    sDialog.cancel();
                    RetrofitManager.get(getActivity()).checkoutUser(this, id);
                })
                .show();
    }
}
