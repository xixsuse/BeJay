package rocks.itsnotrocketscience.bejay.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.ApiManager;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.LoginComponent;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

public class LoginFragment extends BaseFragment<LoginComponent> implements LoginContract.LoginView  {

    @Inject SharedPreferences sharedPreferences;
    @Inject ApiManager apiManager;
    @Inject LoginContract.LoginPresenter loginPresenter;
    @Inject Launcher launcher;

    @Bind(R.id.etUsername) EditText etUsername;
    @Bind(R.id.etPassword) EditText etPassword;
    @Bind(R.id.pbProgress) ProgressBar pbProgress;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setProgressVisible(boolean visible) {
        pbProgress.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoggedIn() {
       launcher.openHome();
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.onViewAttached(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        loginPresenter.onViewDetached();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }

    @OnClick(R.id.btLogin)
    public void login() {
        loginPresenter.login(etUsername.getText().toString(), etPassword.getText().toString());
    }

}