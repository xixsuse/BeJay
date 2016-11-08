package rocks.itsnotrocketscience.bejay.map;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

/**
 * Created by sirfunkenstine on 23/04/16.
 */
public class MapPresenterImpl implements MapContract.MapPresenter, LocationProvider.LocationRetrievedCallback {

    private final Launcher launcher;
    private final Context context;
    private final LocationProvider locationProvider;
    private FetchAddressTask fetchAddressTask;
    private MapContract.MapView view;
    private Marker marker;

    public MapPresenterImpl(Context context, Launcher launcher, LocationProvider locationProvider) {
        this.context = context;
        this.launcher = launcher;
        this.locationProvider = locationProvider;
        locationProvider.buildGoogleApiClient(this);
        setupFetchAddressTask();
    }

    @Override
    public void onViewAttached(MapContract.MapView view) {
        this.view = view;
        locationProvider.connect();

    }

    private void setupFetchAddressTask() {
        OnAddressResolvedCallback callback = address -> launcher.finishMapActivityForResult(marker.getPosition(), address);
        fetchAddressTask = new FetchAddressTask(context.getApplicationContext(), new HandlerOnAddressResolvedCallback(callback));
    }

    @Override
    public void onDestroy() {
        view = null;
        locationProvider.disconnect();
        fetchAddressTask = null;
    }

    @Override
    public void finish(MapActivity mapActivity) {
        fetchAddress();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        view.setGoogleMap(googleMap);
        locationProvider.fetchLocation();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        setMarker(latLng);
    }

    @Override
    public void onViewDetached() {
    }

    private void setMarker(LatLng latLng) {
        if (marker == null) {
            marker = view.addMarker(new MarkerOptions().position(latLng).title("Party").draggable(true));
        } else {
            marker.setPosition(latLng);
        }
    }

    private void fetchAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(context, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
            return;
        }

        fetchAddressTask.setLocation(getLocation());
        fetchAddressTask.run();
    }


    @NonNull
    private Location getLocation() {
        Location location = new Location("");
        location.setLatitude(marker.getPosition().latitude);
        location.setLongitude(marker.getPosition().longitude);
        return location;
    }

    @Override
    public void onLocationRetrieved(LatLng latLng) {
        view.moveToCurrentLocation(latLng);
        setMarker(latLng);
    }

    @Override
    public void requestPermission() {
        view.requestPermission();
    }

    interface OnAddressResolvedCallback {
        void onAddressResolved(String address);
    }

    private static class HandlerOnAddressResolvedCallback implements OnAddressResolvedCallback {
        private final OnAddressResolvedCallback target;

        HandlerOnAddressResolvedCallback(OnAddressResolvedCallback target) {
            this.target = target;
        }

        @Override
        public void onAddressResolved(final String address) {
            new Handler(Looper.getMainLooper()).post(() -> target.onAddressResolved(address));
        }
    }
}
