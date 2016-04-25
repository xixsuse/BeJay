package rocks.itsnotrocketscience.bejay.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
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
 */
public class MapPresenterImpl implements MapContract.MapPresenter {

    private MapContract.MapView view;
    private AddressResultReceiver resultReceiver;
    protected GoogleApiClient mGoogleApiClient;
    private boolean addressRequested = false;
    private final Launcher launcher;
    private final Context context;
    private String addressOutput = "";
    private Marker marker;

    public MapPresenterImpl(Context context, Launcher launcher) {
        this.context = context;
        this.launcher = launcher;
        resultReceiver = new AddressResultReceiver(new Handler());
        buildGoogleApiClient();
    }

    @Override public void onViewAttached(MapContract.MapView view) {
        this.view = view;
        mGoogleApiClient.connect();
    }

    @Override public void onDestroy() {
        view = null;
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
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
    }

    @Override public void onMarkerDragStart(Marker marker) {
    }

    @Override public void onMarkerDrag(Marker marker) {
    }

    @Override public void onMarkerDragEnd(Marker marker) {
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

            if (!Geocoder.isPresent()) {
                Toast.makeText(context, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
                return;
            }
            if (!addressRequested) {
                startIntentService();
            }

        } else {
            view.requestPermission();
        }
    }

    protected void startIntentService() {
        Intent intent = new Intent(context, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        Location location = new Location("");
        location.setLatitude(marker.getPosition().latitude);
        location.setLongitude(marker.getPosition().longitude);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        context.startService(intent);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == Constants.SUCCESS_RESULT) {
                addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            }
            addressRequested = false;
        }
    }
}
