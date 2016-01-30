package rocks.itsnotrocketscience.bejay.login;

/**
 * Created by centralstation on 1/29/16.
 */
public interface LoginContract {
    interface LoginView{
        void setProgressVisible(boolean visible);
        void showError(String error);
        void onLoggedIn();

    }

    interface LoginPresenter{
        void login(String username, String password);
        void onViewAttached(LoginView view);
        void onViewDetached();
        void onDestroy();
    }
}
