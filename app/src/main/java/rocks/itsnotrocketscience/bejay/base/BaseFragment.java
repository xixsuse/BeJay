package rocks.itsnotrocketscience.bejay.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.login.LoginOrRegisterFragment;

/**
 * Created by centralstation on 11/09/15.
 */
public class BaseFragment extends Fragment {

    @Inject
    SharedPreferences sharedPreferences;

    public AppApplication getAppApplication() {
        Activity activity = getActivity();
        if (activity == null)
            return null;
        else
            return (AppApplication) activity.getApplication();
    }

    public void showFragment(Fragment fragment) {
        android.app.FragmentManager manager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppApplication) getActivity().getApplication()).getNetComponent().inject(this);
    }

    protected void logout(Activity activity) {
        sharedPreferences.edit().putBoolean(LoginOrRegisterFragment.IS_LOGGED_IN, false);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        activity.finish();
    }

    public void openActivity(Class<?> calledActivity) {
        Intent myIntent = new Intent(getActivity(), calledActivity);
        this.startActivity(myIntent);
    }

}
