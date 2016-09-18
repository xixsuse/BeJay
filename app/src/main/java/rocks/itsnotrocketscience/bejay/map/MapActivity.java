package rocks.itsnotrocketscience.bejay.map;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.dagger.ActivityModule;
import rocks.itsnotrocketscience.bejay.dagger.DaggerActivityComponent;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

/**
 * Created by sirfunkenstine on 25/03/16.
 *
 */
public class MapActivity extends InjectedActivity<ActivityComponent> implements MapContract.MapView {

    public static final String POSITION = "position";
    public static final String PLACE = "place";

    private final ActivityModule mapModule;

    @Inject public MapContract.MapPresenter mapPresenter;
    @Inject Launcher launcher;

    private GoogleMap googleMap;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    public MapActivity() {
        mapModule = new ActivityModule(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        mapPresenter.onViewAttached(this);
        setupSupportMapFragment();
        setSupportActionBar(toolbar);
    }

    private void setupSupportMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapPresenter);
    }

    @Override public void moveToCurrentLocation(LatLng currentLocation) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }

    @Override public void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    @Override public Marker addMarker(MarkerOptions party) {
        return googleMap.addMarker(party);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            mapPresenter.finish(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .activityModule(mapModule)
                .appComponent(getAppComponent())
                .build();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mapPresenter.onDestroy();
    }

    @Override protected void onResume() {
        super.onResume();
        mapPresenter.onViewAttached(this);
    }

    @Override public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMapClickListener(mapPresenter);
    }
}