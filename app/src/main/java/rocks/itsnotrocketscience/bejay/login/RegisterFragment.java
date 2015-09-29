package rocks.itsnotrocketscience.bejay.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import rocks.itsnotrocketscience.bejay.api.retrofit.CreateUser;
import rocks.itsnotrocketscience.bejay.api.retrofit.LoginUser;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.main.MainActivity;
import rocks.itsnotrocketscience.bejay.models.CmsUser;
import rocks.itsnotrocketscience.bejay.models.Token;


public class RegisterFragment extends BaseFragment {

    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.btRegister)
    Button btRegister;
    private CmsUser user;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConstants.API).build();
        CreateUser createUser = restAdapter.create(CreateUser.class);
        createUser.createUser(getUserObject(), new Callback<CmsUser>() {
            @Override
            public void success(CmsUser eventList, Response response) {
                login();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("yo", "yo");
            }
        });
    }

    public void login() {

        AuthCredentials auth = new AuthCredentials(getUserObject().getUsername(), getUserObject().getPassword());
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConstants.API).build();
        LoginUser loginUser = restAdapter.create(LoginUser.class);


        loginUser.loginUser(ApiConstants.TOKEN, auth, new Callback<Token>() {
            @Override
            public void success(Token token, Response response) {
                getDemoApplication().getSharedPreferences().edit().putString(Constants.TOKEN, token.getToken()).commit();
                Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("fail", "fail");
            }
        });

    }

    public CmsUser getUserObject() {
        if (user == null)
            return new CmsUser("", "", etEmail.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
        else
            return user;
    }
}
