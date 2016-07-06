package rocks.itsnotrocketscience.bejay.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

/**
 * Created by sirfunkenstine on 23/04/16.
 */
public class MapPresenterImpl implements MapContract.MapPresenter {


    private final FetchAddressHandlerThread handlerThread;
    private final Launcher launcher;
    private final Context context;
    private FetchAddressTask fetchAddressTask;
    protected GoogleApiClient mGoogleApiClient;
    private String addressOutput = "";
    private MapContract.MapView view;
    private Marker marker;

    public MapPresenterImpl(Context context, Launcher launcher, FetchAddressHandlerThread handlerThread) {
        this.context = context;
        this.launcher = launcher;
        this.handlerThread = handlerThread;
        buildGoogleApiClient();
    }

    @Override public void onViewAttached(MapContract.MapView view) {
        this.view = view;
        mGoogleApiClient.connect();

    }

    private void setupHandler() {

        handlerThread.start();
        OnAddressResolvedCallback callback = address -> {
            addressOutput = address;
        };
        Handler handler = new Handler(Looper.getMainLooper());
        fetchAddressTask = new FetchAddressTask(context.getApplicationContext(), new HandlerOnAddressResolvedCallback(handler, callback));

    }

    @Override public void onDestroy() {
        view = null;
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        handlerThread.quit();
        fetchAddressTask = null;
    }

    @Override public void finish(MapActivity mapActivity) {
        launcher.finishMapActivityForResult(marker.getPosition(), addressOutput);
    }

    @Override public void onMapReady(GoogleMap googleMap) {
        view.setGoogleMap(googleMap);
        setupLocation();
    }

    @Override public void onConnected(Bundle connectionHint) {
        setupLocation();
    }

    @Override public void onMapClick(LatLng latLng) {
        setMarker(latLng);
        fetchAddress();
    }

    @Override public void onMarkerDragStart(Marker marker) {}

    @Override public void onMarkerDrag(Marker marker) {}

    @Override public void onMarkerDragEnd(Marker marker) {
        fetchAddress();
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override public void onViewDetached() {}

    @Override public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    private void setupLocation() {
        if (!mGoogleApiClient.isConnected()) return;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setupHandler();
            Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (lastKnownLocation != null) {

                LatLng latLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                view.moveToCurrentLocation(latLng);
                setMarker(latLng);
                fetchAddress();
            }

        } else {
            view.requestPermission();
        }
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

        handlerThread.cancelTask(fetchAddressTask);
        fetchAddressTask.setLocation(getLocation());
        handlerThread.postTask(fetchAddressTask);
    }


    @NonNull private Location getLocation() {
        Location location = new Location("");
        location.setLatitude(marker.getPosition().latitude);
        location.setLongitude(marker.getPosition().longitude);
        return location;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public interface OnAddressResolvedCallback {
        void onAddressResolved(String address);
    }

    public static class HandlerOnAddressResolvedCallback implements OnAddressResolvedCallback {
        private final Handler handler;
        private final OnAddressResolvedCallback target;

        public HandlerOnAddressResolvedCallback(Handler handler, OnAddressResolvedCallback target) {
            this.handler = handler;
            this.target = target;
        }

        @Override
        public void onAddressResolved(final String address) {
            handler.post(() -> target.onAddressResolved(address));
        }
    }
}
