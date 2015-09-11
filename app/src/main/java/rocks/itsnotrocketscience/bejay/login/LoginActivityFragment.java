package rocks.itsnotrocketscience.bejay.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rocks.itsnotrocketscience.bejay.BaseFragment;
import rocks.itsnotrocketscience.bejay.MainActivity;
import rocks.itsnotrocketscience.bejay.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends BaseFragment{

    @Bind(R.id.btLogin)    Button btLogin;
    @Bind(R.id.btRegister) Button btRegister;
    public static final String IS_LOGGED_IN = "isLoggedIn";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.btRegister)
    public void register() {
    }

    @OnClick(R.id.btLogin)
    public void login() {
        getDemoApplication().getSharedPreferences().edit().putBoolean(IS_LOGGED_IN, true).commit();
        Toast.makeText(getContext(), "Logged in", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
