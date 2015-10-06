package rocks.itsnotrocketscience.bejay.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.ApiConstants;
import rocks.itsnotrocketscience.bejay.api.retrofit.AuthCredentials;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.LoginUser;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.main.MainActivity;
import rocks.itsnotrocketscience.bejay.models.Token;

public class LoginFragment extends BaseFragment {

    @Bind(R.id.etUsername) EditText etUsername;
    @Bind(R.id.etPassword) EditText etPassword;
    @Bind(R.id.pbProgress)
    ProgressBar pbProgress;
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConstants.API).build();
        LoginUser loginUser = restAdapter.create(LoginUser.class);
        toggleProgress(true);

        loginUser.loginUser(ApiConstants.TOKEN, auth, new Callback<Token>() {
            @Override
            public void success(Token token, Response response) {
                getDemoApplication().getSharedPreferences().edit().putString(Constants.TOKEN, token.getToken()).commit();
                Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
                toggleProgress(false);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                toggleProgress(false);
                Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void toggleProgress(boolean on) {
        pbProgress.setVisibility(on ? View.VISIBLE : View.GONE);
    }
}