package rocks.itsnotrocketscience.bejay.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.LoginComponent;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

public class LoginFragment extends BaseFragment<LoginComponent> implements LoginContract.LoginView  {

    @Bind(R.id.btLoginFacebook) LoginButton btLoginFacebook;
    @Bind(R.id.pbProgress) ProgressBar pbProgress;
    @Inject LoginContract.LoginPresenter loginPresenter;
    @Inject Launcher launcher;
    CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
    }

    public static Fragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        setupFacebookLoginButton(btLoginFacebook);
        return view;
    }
    @OnClick(R.id.btLoginFacebook)
    public void login() {
        launcher.openLogin();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginPresenter.onViewAttached(this);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setProgressVisible(boolean visible) {
        pbProgress.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String error) {}

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

    private void setupFacebookLoginButton( LoginButton btLoginFacebook) {
        btLoginFacebook.setFragment(this);
        btLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginPresenter.verifyUser(loginResult);
            }
            @Override
            public void onCancel() {}
            @Override
            public void onError(FacebookException exception) {}
        });
    }

}