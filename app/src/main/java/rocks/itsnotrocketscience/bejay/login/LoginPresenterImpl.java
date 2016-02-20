package rocks.itsnotrocketscience.bejay.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.login.LoginResult;

import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.SocialAuth;
import rocks.itsnotrocketscience.bejay.managers.ServiceFactory;
import rocks.itsnotrocketscience.bejay.models.ConvertTokenResponse;
import rocks.itsnotrocketscience.bejay.models.Token;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by centralstation on 1/29/16.
 *
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
    public void verifyUser(LoginResult loginResult) {

        view.setProgressVisible(true);
        SocialAuth socialAuth = ServiceFactory.createGcmRetrofitService(SocialAuth.class);
        String token = loginResult.getAccessToken().getToken();
        socialAuth.convertToken("convert_token",
                "UVRspdWBRqf0ofty1J6bZqRoNiN5BKNHZL2rRlby",
                "1T0WT2HcIvChkHilNRCFYmDqDbeLHGRYEmby2HHdNrmCLkti3N7InyfGldMLVOetSF4YHgT0zP5mKazPUo3DqG4e1gtyvtBMkIYUaFpb4lBgDHIsUioB8wEJ0m6ntvoM",
                "facebook",
                token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ConvertTokenResponse>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public final void onError(Throwable e) {
                        view.setProgressVisible(false);
                        view.showError(e.getLocalizedMessage());
                    }

                    @Override
                    public final void onNext(ConvertTokenResponse response) {
                        sharedPreferences.edit().putString(Constants.TOKEN,token) .apply();
                        sharedPreferences.edit().putBoolean(Constants.IS_LOGGED_IN,true).apply();
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
