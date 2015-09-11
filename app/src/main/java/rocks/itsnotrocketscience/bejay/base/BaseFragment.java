package rocks.itsnotrocketscience.bejay.base;

import android.app.Activity;
import android.support.v4.app.Fragment;

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
}
