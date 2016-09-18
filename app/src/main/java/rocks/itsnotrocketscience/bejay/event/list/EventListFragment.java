package rocks.itsnotrocketscience.bejay.event.list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.event.list.EventListContract.EventListPresenter.CheckInError;
import rocks.itsnotrocketscience.bejay.managers.Launcher;
import rocks.itsnotrocketscience.bejay.models.Event;

public class EventListFragment extends BaseFragment<ActivityComponent> implements ItemClickListener<Event>, EventListContract.EventListView, SearchView.OnQueryTextListener {

    @Inject public EventListContract.EventListPresenter eventListPresenter;
    @Inject public Launcher launcher;

    @Bind(R.id.rvEventList)
    public RecyclerView recyclerView;
    @Bind(R.id.progress)
    public ProgressBar progressIndicator;
    @Bind(R.id.rlError)
    public RelativeLayout rlError;
    @Bind(R.id.fab)
    public FloatingActionButton fab;
    @Bind(R.id.btnRetry)
    Button btnRetry;
    private EventListAdapter adapter;
    private List<Event> eventList;

    public static Fragment newInstance(EventListType type) {
        Fragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putSerializable("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setRetainInstance(true);
        eventListPresenter.setListType((EventListType) getArguments().getSerializable("type"));
        eventList = new ArrayList<>();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        addSnackBar();
    }

    private void addSnackBar() {
        fab.setOnClickListener(v -> eventListPresenter.openCreateEvent());
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
    public void onCheckInFailed(Event event, @CheckInError int reason) {
        switch (reason) {
            case EventListContract.EventListPresenter.CHECK_IN_CHECKOUT_NEEDED: {
                displayAlert(event);
                break;
            }
            case EventListContract.EventListPresenter.CHECK_IN_FAILED: {
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
    public void showError(String text) {
        rlError.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
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
                    eventListPresenter.openEvent();

                })
                .setConfirmClickListener(sDialog -> {
                    sDialog.cancel();
                    eventListPresenter.checkIn(event, true);
                })
                .show();
    }

    @Override
    public void setProgressVisible(boolean visible) {
        if (visible) {
            progressIndicator.setVisibility(View.VISIBLE);
        } else {
            progressIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (eventListPresenter.getType() == EventListType.SEARCH) {
            inflater.inflate(R.menu.event_search, menu);
            MenuItem searchMenuItem = menu.findItem(R.id.action_event_search);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
            searchView.setOnQueryTextListener(this);
            setSearchableMenuItem(searchMenuItem);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        eventListPresenter.searchEvent(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void setSearchableMenuItem(MenuItem searchMenuItem) {
        MenuItemCompat.expandActionView(searchMenuItem);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        launcher.search();
                        return true;
                    }
                });
    }

}
