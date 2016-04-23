package rocks.itsnotrocketscience.bejay.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import rocks.itsnotrocketscience.bejay.managers.Launcher;

/**
 * Created by sirfunkenstine on 23/04/16.
 *
 */
public class MapPresenterImpl implements MapContract.MapPresenter {

    MapContract.MapView view;
    Marker marker;
    private final Context context;
    private final Launcher launcher;

    public MapPresenterImpl(Context context, Launcher launcher) {
        this.context = context;
        this.launcher=launcher;
    }

    @Override public void onViewAttached(MapContract.MapView view) {
        this.view = view;
    }

    @Override public void onViewDetached() {
    }

    @Override public void onDestroy() {
        view = null;
    }

    @Override public void finish(MapActivity mapActivity) {
        launcher.finishMapActivityForResult(marker.getPosition());
    }

    @Override public void onMapReady(GoogleMap googleMap) {
        view.setGoogleMap(googleMap);
        googleMap.setOnMarkerDragListener(this);
        googleMap.setOnMapClickListener(this);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            LatLng latLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            view.moveToCurrentLocation(latLng);

            marker = view.addMarker(new MarkerOptions().position(latLng).title("Party").draggable(true));

        } else {
            view.requestPermission();
        }
    }

    @Override public void onMapClick(LatLng latLng) {
        marker.setPosition(latLng);
    }

    @Override public void onMarkerDragStart(Marker marker) {

    }

    @Override public void onMarkerDrag(Marker marker) {

    }

    @Override public void onMarkerDragEnd(Marker marker) {

    }
}
