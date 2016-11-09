package rocks.itsnotrocketscience.bejay.event.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.event.list.EventListType;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

public class EventSearchFragment extends BaseFragment<ActivityComponent> {

    @Inject public Launcher launcher;

    public static EventSearchFragment newInstance() {
        return new EventSearchFragment();
    }

    public static String getTitle() {
        return "Home";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_events, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (R.id.search == id) {
            launcher.openEventList(EventListType.SEARCH);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btAllPublicEvents)
    public void findEvents() {
        launcher.openEventList(EventListType.ALL);
    }

    @OnClick(R.id.btFindFriendsEvents)
    public void findFriendEvents() {
        launcher.openEventList(EventListType.FRIENDS);
    }


    @OnClick(R.id.btEventsNearMe)
    public void findEventsNearMe() {
        launcher.openEventList(EventListType.PUBLIC_LOCAL);
    }

    @OnClick(R.id.btSearchEvents)
    public void searchEvents() {
        launcher.openEventList(EventListType.SEARCH);
    }
}
