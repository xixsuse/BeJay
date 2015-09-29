package rocks.itsnotrocketscience.bejay.main.nav_items;

import android.app.Fragment;
import android.content.Context;
import android.app.FragmentManager;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseActivity;
import rocks.itsnotrocketscience.bejay.main.MainActivity;

/**
 * Created by centralstation on 11/09/15.
 *
 */
public class FragmentNavItem implements NavItem {

    Fragment fragment;
    String title;
    Context context;

    public FragmentNavItem(Context context, String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
        this.context=context;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public void onSelected() {
        FragmentManager fragmentManager = ((BaseActivity)context).getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
