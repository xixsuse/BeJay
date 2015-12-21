package rocks.itsnotrocketscience.bejay.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.RetrofitError;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.retrofit.AuthCredentials;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.base.AppApplication;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.main.MainActivity;
import rocks.itsnotrocketscience.bejay.managers.RetrofitManager;
import rocks.itsnotrocketscience.bejay.models.CmsUser;
import rocks.itsnotrocketscience.bejay.models.Token;


public class RegisterFragment extends BaseFragment implements RetrofitManager.LoginListener, RetrofitManager.RegisterListener {

    @Inject SharedPreferences sharedPreferences;
    @Bind(R.id.pbProgress) ProgressBar pbProgress;
    @Bind(R.id.etEmail) EditText etEmail;
    @Bind(R.id.etPassword) EditText etPassword;
    @Bind(R.id.btRegister) Button btRegister;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppApplication) getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btRegister)
    public void register() {
        toggleProgress(true);
        RetrofitManager.get(getActivity()).registerUser(getUserObject(), this);
    }

    public void login() {
        AuthCredentials auth = new AuthCredentials(getUserObject().getUsername(), getUserObject().getPassword());
        RetrofitManager.get(getActivity()).loginUser(auth, this);
        toggleProgress(true);
    }

    public CmsUser getUserObject() {
          return new CmsUser("","", "", "", etEmail.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
    }

    private void toggleProgress(boolean on) {
        pbProgress.setVisibility(on ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoggedIn(Token token, RetrofitError error) {
        if (token != null) {
            sharedPreferences.edit().putString(Constants.TOKEN, token.getToken()).apply();
            Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
            toggleProgress(false);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        } else {
            toggleProgress(false);
            Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRegistered(CmsUser user, RetrofitError error) {
        if(user!=null){
           login();
        }
        else{
            toggleProgress(false);
            Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
