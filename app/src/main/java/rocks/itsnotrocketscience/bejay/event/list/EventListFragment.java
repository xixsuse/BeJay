package rocks.itsnotrocketscience.bejay.event.list;

import android.app.Fragment;
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

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.RetrofitError;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.AppApplication;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.LaunchManager;
import rocks.itsnotrocketscience.bejay.managers.RetrofitListeners;
import rocks.itsnotrocketscience.bejay.managers.RetrofitManager;
import rocks.itsnotrocketscience.bejay.models.Event;

public class EventListFragment extends BaseFragment implements ItemClickListener, RetrofitManager.CheckoutListener, RetrofitListeners.CheckInListener, EventListContract.EventListView {

    @Inject AccountManager accountManager;
    @Inject RetrofitManager retrofitManager;
    @Inject EventListPresenterImpl eventListPresenter;

    @Bind(R.id.rvEventList)
    RecyclerView recyclerView;
    @Bind(R.id.rlError)
    RelativeLayout rlError;
    @Bind(R.id.btnRetry)
    Button btnRetry;
    EventListAdapter adapter;
    List<Event> eventList;

    public static Fragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppApplication().getNetComponent().inject(this);
        eventList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        eventListPresenter.onViewAttached(this);
        getFeed();
    }

    @Override
    public void onPause() {
        super.onPause();
        eventListPresenter.onViewDetached();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventListPresenter.onDestroy();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new EventListAdapter(eventList);
        adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
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
        rlError.setVisibility(View.GONE);
        eventListPresenter.loadEvents();
    }

    @Override
    public void onClick(View view, int position) {
        boolean isCheckedIn = accountManager.isCheckedIn();
        int eventId = eventList.get(position).getId();
        if (!isCheckedIn) {
            retrofitManager.checkInUser(this, eventList.get(position).getId());
        } else if (eventId == accountManager.getCheckedInEventId()) {
            LaunchManager.launchEvent(eventId, getActivity());
        } else {
            displayAlert(eventList.get(position).getId());
        }
    }

    @Override
    public void setProgressVisible(boolean visible) {
        Toast.makeText(getActivity(), "setProgressVisible(" +visible + ")", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventsLoaded(List<Event> events) {
        this.eventList.clear();
        this.eventList.addAll(events);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        rlError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckedOut(int id, RetrofitError error) {
        if (error != null) {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            accountManager.clearCheckin();
            retrofitManager.checkInUser(this, id);
        }
    }

    @Override
    public void onCheckedIn(int id, RetrofitError error) {
        if (error != null) {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Checked in", Toast.LENGTH_LONG).show();
            accountManager.setCheckedIn(id);
            LaunchManager.launchEvent(id, getActivity());
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
                    LaunchManager.launchEvent(accountManager.getCheckedInEventId(), getActivity());

                })
                .setConfirmClickListener(sDialog -> {
                    sDialog.cancel();
                    retrofitManager.checkoutUser(this, id);
                })
                .show();
    }

}
