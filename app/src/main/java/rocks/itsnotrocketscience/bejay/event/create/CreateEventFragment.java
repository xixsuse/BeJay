package rocks.itsnotrocketscience.bejay.event.create;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;

/**
 * Created by sirfunkenstine on 22/03/16.
 */
public class CreateEventFragment extends BaseFragment<ActivityComponent> {


    public static Fragment newInstance() {
        return new CreateEventFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_event_create, container, false);

        return rootView;
    }
}
