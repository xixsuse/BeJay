package rocks.itsnotrocketscience.bejay.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import rocks.itsnotrocketscience.bejay.R;

/**
 * Created by centralstation on 11/09/15.
 *
 */
public class BaseFragment extends Fragment{

    public AppApplication getDemoApplication(){
        Activity activity = getActivity();
        if (activity == null)
            return null;
        else
            return (AppApplication)activity.getApplication();
    }

    public void showFragment(Fragment fragment)
    {
        android.app.FragmentManager manager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
