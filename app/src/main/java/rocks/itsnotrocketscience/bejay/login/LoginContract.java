package rocks.itsnotrocketscience.bejay.login;

import com.facebook.login.LoginResult;

/**
 * Created by centralstation on 1/29/16.
 *
 */
public interface LoginContract {
    interface LoginView{
        void setProgressVisible(boolean visible);
        void showError(String error);
        void onLoggedIn();

    }

    interface LoginPresenter{
        void onViewAttached(LoginView view);
        void onViewDetached();
        void onDestroy();
        void registerUser(LoginResult loginResult);
    }
}
