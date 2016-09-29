package rocks.itsnotrocketscience.bejay.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.login.LoginResult;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.SocialAuth;
import rocks.itsnotrocketscience.bejay.managers.ServiceFactory;
import rocks.itsnotrocketscience.bejay.models.ConvertTokenResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by centralstation on 1/29/16.
 */
public class LoginPresenterImpl implements LoginContract.LoginPresenter {
    private LoginContract.LoginView view;
    private final SharedPreferences sharedPreferences;
    private final Context context;

    public LoginPresenterImpl(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void registerUser(LoginResult loginResult) {

        view.setProgressVisible(true);
        String token = loginResult.getAccessToken().getToken();

        SocialAuth socialAuth = ServiceFactory.createRetrofitServiceNoAuth(SocialAuth.class);
        socialAuth.convertToken(context.getString(R.string.convert_token),
                context.getString(R.string.client_id),
                context.getString(R.string.client_secret),
                context.getString(R.string.provider),
                token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ConvertTokenResponse>() {
                    @Override public void onCompleted() {}
                    @Override public final void onError(Throwable e) {
                        view.setProgressVisible(false);
                        view.showError(e.getLocalizedMessage());
                    }

                    @Override public final void onNext(ConvertTokenResponse response) {
                        sharedPreferences.edit().putString(Constants.TOKEN, token).apply();
                        sharedPreferences.edit().putBoolean(Constants.IS_LOGGED_IN, true).apply();
                        sharedPreferences.edit().putString(Constants.TOKEN_TYPE, response.tokenType).apply();
                        sharedPreferences.edit().putString(Constants.REFRESH_TOKEN, response.refreshToken).apply();

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
