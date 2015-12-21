package rocks.itsnotrocketscience.bejay.login;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;

public class LoginOrRegisterFragment extends BaseFragment {

    public static final String IS_LOGGED_IN = "isLoggedIn";
    @Bind(R.id.btLogin)
    Button btLogin;
    @Bind(R.id.btRegister)
    Button btRegister;

    public static Fragment newInstance() {
        return new LoginOrRegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_or_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btRegister)
    public void register() {
        showFragment(RegisterFragment.newInstance());
    }

    @OnClick(R.id.btLogin)
    public void login() {
        showFragment(LoginFragment.newInstance());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
