package rocks.itsnotrocketscience.bejay.home;

import android.os.Bundle;
import android.view.LayoutInflater;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_events, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btAllPublicEvents)
    public void findEvents() {
        launcher.openEventList(EventListType.ALL);
    }

    @OnClick(R.id.btFindFriendsEvents)
    public void findFriendEvents() {
        launcher.openEventList(EventListType.FRIENDS);
    }

}
