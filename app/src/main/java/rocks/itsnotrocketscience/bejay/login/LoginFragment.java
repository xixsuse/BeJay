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
import retrofit.RetrofitError;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.AuthCredentials;
import rocks.itsnotrocketscience.bejay.base.AppApplication;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.main.MainActivity;
import rocks.itsnotrocketscience.bejay.managers.RetrofitManager;
import rocks.itsnotrocketscience.bejay.models.Token;

public class LoginFragment extends BaseFragment implements RetrofitManager.LoginListener {

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
        retrofitManager.loginUser(auth, this);
        toggleProgress(true);

    }

    private void toggleProgress(boolean on) {
        pbProgress.setVisibility(on ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoggedIn(Token token, RetrofitError error) {
        if (token != null) {

            sharedPreferences.edit().putString(Constants.TOKEN, token.getToken()).apply();
            sharedPreferences.edit().putString(Constants.EMAIL, etUsername.getText().toString()).apply();
            sharedPreferences.edit().putString(Constants.USERNAME, etUsername.getText().toString()).apply();
            Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
            toggleProgress(false);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        } else {
            toggleProgress(false);
            Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
}