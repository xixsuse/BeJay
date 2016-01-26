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
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.CreateUser;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.main.MainActivity;
import rocks.itsnotrocketscience.bejay.managers.RetrofitManager;
import rocks.itsnotrocketscience.bejay.managers.ServiceFactory;
import rocks.itsnotrocketscience.bejay.models.CmsUser;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RegisterFragment extends BaseFragment {

    @Inject SharedPreferences sharedPreferences;
    @Inject RetrofitManager retrofitManager;
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
        getAppApplication().getNetComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btRegister)
    public void register() {
        toggleProgress(true);

        CreateUser createUser = ServiceFactory.createRetrofitService(CreateUser.class);
        createUser.createUser(getUserObject())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CmsUser>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public final void onError(Throwable e) {
                        toggleProgress(false);
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public final void onNext(CmsUser response) {
                        toggleProgress(false);
                        login(response.getToken());
                    }
                });

    }

    public CmsUser getUserObject() {
        return new CmsUser("", "", "", "", etEmail.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
    }

    private void toggleProgress(boolean visible) {
        pbProgress.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    public void login(String token) {
        if (token != null) {
            sharedPreferences.edit().putString(Constants.TOKEN, token).apply();
            Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
            toggleProgress(false);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

}
