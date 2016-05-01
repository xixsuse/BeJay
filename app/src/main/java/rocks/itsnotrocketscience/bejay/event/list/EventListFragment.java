package rocks.itsnotrocketscience.bejay.event.list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.ApiManager;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.event.list.EventListContract.EventListPresenter.CheckInError;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.Launcher;
import rocks.itsnotrocketscience.bejay.models.Event;

public class EventListFragment extends BaseFragment<ActivityComponent> implements ItemClickListener<Event>, EventListContract.EventListView {

    @Inject AccountManager accountManager;
    @Inject ApiManager apiManager;
    @Inject EventListContract.EventListPresenter eventListPresenter;
    @Inject Launcher launcher;

    @Bind(R.id.rvEventList) RecyclerView recyclerView;
    @Bind(R.id.progress) ProgressBar progressIndicator;
    @Bind(R.id.rlError) RelativeLayout rlError;
    @Bind(R.id.btnRetry) Button btnRetry;
    @Bind(R.id.fab) FloatingActionButton fab;
    EventListAdapter adapter;
    List<Event> eventList;

    public static Fragment newInstance() {
        return new EventListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setRetainInstance(true);
        eventList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        addSnackBar();
    }

    private void addSnackBar() {
        fab.setOnClickListener(v -> launcher.openCreateEvent());
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
    public void onClick(Event event, int position) {
        eventListPresenter.checkIn(event, false);
    }

    @Override
    public void onChecking(Event event) {
        launcher.openEvent(event.getId());
    }

    @Override
    public void onCheckInFailed(Event event, @CheckInError int reason) {
        switch (reason) {
            case EventListContract.EventListPresenter.CHECK_IN_CHECKOUT_NEEDED : {
                displayAlert(event);
                break;
            }
            case EventListContract.EventListPresenter.CHECK_IN_FAILED : {
                Toast.makeText(getActivity(), "check in failed", Toast.LENGTH_LONG).show();
                break;
            }
        }
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

    private void displayAlert(Event event) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Already checked in to another event!")
                .setContentText("Go to checked in event or check in to this one?")
                .setCancelText("Current Event")
                .setConfirmText("This one")
                .showCancelButton(true)
                .setCancelClickListener(sDialog -> {
                    sDialog.cancel();
                    launcher.openEvent(accountManager.getCheckedInEventId());
                })
                .setConfirmClickListener(sDialog -> {
                    sDialog.cancel();
                    eventListPresenter.checkIn(event, true);
                })
                .show();
    }


    @Override
    public void setProgressVisible(boolean visible) {
        if(visible) {
            progressIndicator.setVisibility(View.VISIBLE);
        } else {
            progressIndicator.setVisibility(View.GONE);
        }
    }

}
