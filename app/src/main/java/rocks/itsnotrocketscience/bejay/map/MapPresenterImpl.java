package rocks.itsnotrocketscience.bejay.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ResultReceiver;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

/**
 * Created by sirfunkenstine on 23/04/16.
 *
 */
public class MapPresenterImpl implements MapContract.MapPresenter {


    private final FetchAddressHandlerThread handlerThread;
    private final Launcher launcher;
    private final Context context;
    private FetchAddressTask fetchAddressTask;
    protected GoogleApiClient mGoogleApiClient;
    private String addressOutput = "";
    private MapContract.MapView view;
    private Handler handler;
    private Marker marker;

    public MapPresenterImpl(Context context, Launcher launcher, FetchAddressHandlerThread handlerThread) {
        this.context = context;
        this.launcher = launcher;
        this.handlerThread=handlerThread;
        buildGoogleApiClient();
        setupHandler();
    }

    @Override public void onViewAttached(MapContract.MapView view) {
        this.view = view;
        mGoogleApiClient.connect();

    }

    private void setupHandler() {

        handlerThread.start();
        handler = new Handler(handlerThread.getLooper(),this);
        handlerThread.prepareHandler(handler);
        fetchAddressTask = new FetchAddressTask(context.getApplicationContext(), handler);

    }

    @Override public void onDestroy() {
        view = null;
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            handlerThread.quit();
            fetchAddressTask=null;
        }
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
        marker.setPosition(latLng);
        fetchAddress();
    }

    @Override public void onMarkerDragStart(Marker marker) {
    }

    @Override public void onMarkerDrag(Marker marker) {
    }

    @Override public void onMarkerDragEnd(Marker marker) {
        fetchAddress();
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override public void onViewDetached() {
    }

    @Override public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    private void setupLocation() {
        if (!mGoogleApiClient.isConnected()) return;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LatLng latLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

            view.moveToCurrentLocation(latLng);
            marker = view.addMarker(new MarkerOptions().position(latLng).title("Party").draggable(true));

            fetchAddress();

        } else {
            view.requestPermission();
        }
    }

    private void fetchAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(context, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
            return;
        }
        handler.removeCallbacks(fetchAddressTask);
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

    @Override public boolean handleMessage(Message msg) {
        addressOutput = msg.getData().getString(Constants.RESULT_DATA_KEY);
        return true;
    }
}
