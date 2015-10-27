package rocks.itsnotrocketscience.bejay.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.login.LoginOrRegisterFragment;

/**
 * Created by centralstation on 11/09/15.
 *
 */
public class BaseFragment extends Fragment{

    public AppApplication getAppApplication(){
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

    protected void logout(Activity activity) {
        ((BaseActivity) activity).getAppApplication().getSharedPreferences().edit().putBoolean(LoginOrRegisterFragment.IS_LOGGED_IN, false);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        activity.finish();
    }

    public void openActivity(Class<?> calledActivity) {
        Intent myIntent = new Intent(getActivity(), calledActivity);
        this.startActivity(myIntent);
    }

}
