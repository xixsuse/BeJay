package rocks.itsnotrocketscience.bejay.login;

import android.content.Context;
import android.content.SharedPreferences;

import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.AuthCredentials;
import rocks.itsnotrocketscience.bejay.api.retrofit.LoginUser;
import rocks.itsnotrocketscience.bejay.managers.ServiceFactory;
import rocks.itsnotrocketscience.bejay.models.Token;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by centralstation on 1/29/16.
 */
public class LoginPresenterImpl implements LoginContract.LoginPresenter{
    private  LoginContract.LoginView view;
    private final SharedPreferences sharedPreferences;
    private final Context context;

    public LoginPresenterImpl(Context context,  SharedPreferences sharedPreferences){
       this.context=context;
       this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void login(String username, String password) {

        AuthCredentials auth = new AuthCredentials(username, password);
        view.setProgressVisible(true);

        LoginUser loginUser = ServiceFactory.createRetrofitService(LoginUser.class);
        loginUser.loginUser(Constants.TOKEN_AUTH, auth)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public final void onError(Throwable e) {
                        view.setProgressVisible(false);
                        view.showError(e.getLocalizedMessage());
                    }

                    @Override
                    public final void onNext(Token response) {
                        sharedPreferences.edit().putString(Constants.TOKEN, response.getToken()).apply();
                        sharedPreferences.edit().putString(Constants.EMAIL, username).apply();
                        sharedPreferences.edit().putString(Constants.USERNAME, password).apply();

                        view.setProgressVisible(false);
                        view.onLoggedIn();
                    }
                });

    }

    @Override
    public void onViewAttached(LoginContract.LoginView view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void onDestroy() {

    }
}
