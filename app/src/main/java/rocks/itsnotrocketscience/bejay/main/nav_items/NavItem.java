package rocks.itsnotrocketscience.bejay.main.nav_items;

/**
 * Created by centralstation on 11/09/15.
 */
interface NavItem {
    String getTitle();

    void onSelected();
}
