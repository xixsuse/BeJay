package rocks.itsnotrocketscience.bejay.event.create;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.dagger.ActivityModule;
import rocks.itsnotrocketscience.bejay.dagger.DaggerActivityComponent;

public class EventCreateActivity extends InjectedActivity<ActivityComponent> {

    @Bind(R.id.toolbar)
    private Toolbar toolbar;

    private final ActivityModule activityModule;
    private ActivityComponent activityComponent;

    public EventCreateActivity() {
        this.activityModule = new ActivityModule(this);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getComponent().inject(this);

        setContentView(R.layout.activity_event_create);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.event_create_container, EventCreateFragment.newInstance())
                    .commitAllowingStateLoss();
        }
    }

    @Override public ActivityComponent getComponent() {
        return activityComponent = DaggerActivityComponent.builder()
                .activityModule(activityModule)
                .appComponent(getAppComponent())
                .build();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled;
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                handled = true;
                break;
            }
            default: {
                handled = super.onOptionsItemSelected(item);
            }
        }

        return handled;
    }
}

