package rocks.itsnotrocketscience.bejay.main.nav_items;

import android.content.Context;
import android.content.Intent;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.login.LoginActivity;
import rocks.itsnotrocketscience.bejay.main.MainActivity;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;

/**
 * Created by centralstation on 11/09/15.
 */
public class LoginNavItem implements NavItem {
    private final Context context;
    private final AccountManager accountManager;

    public LoginNavItem(Context context, AccountManager accountManager) {

        this.context = context.getApplicationContext();
        this.accountManager = accountManager;
    }

    @Override
    public String getTitle() {
        return context.getString(R.string.logout);
    }

    @Override
    public void onSelected() {
        if (context instanceof MainActivity) {
            accountManager.clearLogin();
            Intent intent = new Intent((context), LoginActivity.class);
            context.startActivity(intent);
            ((MainActivity) context).finish();
        }
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
