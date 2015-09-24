package rocks.itsnotrocketscience.bejay.main.nav_items;

import android.content.Context;
import android.content.Intent;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.login.LoginFragment;
import rocks.itsnotrocketscience.bejay.main.MainActivity;

/**
 * Created by centralstation on 11/09/15.
 *
 */
public class LoginNavItem implements NavItem {
    private  Context context;

    @Override
    public String getTitle() {
        return  context.getString(R.string.logout);
    }

    public LoginNavItem(Context context) {
        this.context = context;
    }

    @Override
    public void onSelected() {
        if(context instanceof MainActivity){
            ((MainActivity)context).getAppApplication().getSharedPreferences().edit().putBoolean(LoginFragment.IS_LOGGED_IN, false);
            Intent intent = new Intent((context), LoginActivity.class);
            context.startActivity(intent);
            ((MainActivity)context).finish();
        }
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
