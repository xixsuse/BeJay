package rocks.itsnotrocketscience.bejay.dagger;

import dagger.Component;
import rocks.itsnotrocketscience.bejay.managers.Launcher;
import rocks.itsnotrocketscience.bejay.map.MapActivity;

/**
 * Created by sirfunkenstine on 07/05/16.
 */
@PerActivity
@Component(modules = MapModule.class, dependencies = {AppComponent.class})
public interface MapComponent {
    Launcher launcher();

    void inject(MapActivity loginActivity);
}
