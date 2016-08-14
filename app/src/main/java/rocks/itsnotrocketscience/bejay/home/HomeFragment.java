package rocks.itsnotrocketscience.bejay.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import at.markushi.ui.CircleButton;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

public class HomeFragment extends BaseFragment<ActivityComponent> {

    @Inject
    private Launcher launcher;
    @Bind(R.id.btFindEvents)CircleButton btFindEvents;

    public static HomeFragment newInstance() {
        return new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btFindEvents)
    public void findEvents() {
        launcher.openEventList();
    }

}
