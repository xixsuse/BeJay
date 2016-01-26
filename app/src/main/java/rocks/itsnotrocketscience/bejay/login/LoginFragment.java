package rocks.itsnotrocketscience.bejay.login;

import android.content.Intent;
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
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.AuthCredentials;
import rocks.itsnotrocketscience.bejay.api.retrofit.LoginUser;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.main.MainActivity;
import rocks.itsnotrocketscience.bejay.managers.RetrofitManager;
import rocks.itsnotrocketscience.bejay.managers.ServiceFactory;
import rocks.itsnotrocketscience.bejay.models.Token;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginFragment extends BaseFragment  {

    @Inject SharedPreferences sharedPreferences;
    @Inject RetrofitManager retrofitManager;
    @Bind(R.id.etUsername) EditText etUsername;
    @Bind(R.id.etPassword) EditText etPassword;
    @Bind(R.id.pbProgress) ProgressBar pbProgress;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppApplication().getNetComponent().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btLogin)
    public void login() {

        AuthCredentials auth = new AuthCredentials(etUsername.getText().toString(), etPassword.getText().toString());
        toggleProgress(true);

        LoginUser loginUser = ServiceFactory.createRetrofitService(LoginUser.class);
        loginUser.loginUser(Constants.TOKEN_AUTH, auth)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public final void onError(Throwable e) {
                        toggleProgress(false);
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public final void onNext(Token response) {
                        toggleProgress(false);
                        login(response);
                    }
                });

    }

    private void toggleProgress(boolean on) {
        pbProgress.setVisibility(on ? View.VISIBLE : View.GONE);
    }

    public void login(Token token) {
        if (token != null) {
            sharedPreferences.edit().putString(Constants.TOKEN, token.getToken()).apply();
            sharedPreferences.edit().putString(Constants.EMAIL, etUsername.getText().toString()).apply();
            sharedPreferences.edit().putString(Constants.USERNAME, etUsername.getText().toString()).apply();
            Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
            toggleProgress(false);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }
}