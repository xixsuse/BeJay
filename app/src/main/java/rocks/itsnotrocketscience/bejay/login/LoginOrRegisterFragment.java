package rocks.itsnotrocketscience.bejay.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
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


/**
 * This class does not inherit from base because facebook login requires support.v4.app.Fragment
 */
public class LoginOrRegisterFragment extends BaseFragment<LoginComponent> {

    @Inject Launcher launcher;

    CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    public static final String IS_LOGGED_IN = "isLoggedIn";
    @Bind(R.id.btLogin) Button btLogin;
    @Bind(R.id.btRegister) Button btRegister;

    public static Fragment newInstance() {
        return new LoginOrRegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_or_register, container, false);
        ButterKnife.bind(this, view);
        LoginButton btLoginFacebook = (LoginButton)view.findViewById(R.id.btLoginFacebook);
        setupFacebookLoginButton(btLoginFacebook);
        return view;
    }

    private void setupFacebookLoginButton( LoginButton btLoginFacebook) {

        btLoginFacebook.setFragment(this);
        // Callback registration
        btLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @OnClick(R.id.btRegister)
    public void register() {
        launcher.openRegistration();
    }

    @OnClick(R.id.btLogin)
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
