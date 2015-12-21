package rocks.itsnotrocketscience.bejay.dagger;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by lduf0001 on 21/12/15.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}