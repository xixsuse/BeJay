package rocks.itsnotrocketscience.bejay.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.login.LoginFragment;

/**
 * Created by centralstation on 11/09/15.
 *
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isLoggedIn()){
            logout();
        }
    }

    private void logout() {
        getAppApplication().getSharedPreferences().edit().putBoolean(LoginFragment.IS_LOGGED_IN, false);
        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    public AppApplication getAppApplication(){
        return (AppApplication)getApplication();
    }

    protected boolean isLoggedIn(){
        return getAppApplication().getSharedPreferences().getBoolean(LoginFragment.IS_LOGGED_IN, false);
    }

}
