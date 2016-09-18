package rocks.itsnotrocketscience.bejay.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by centralstation on 18/09/16.
 */

public class LocationProvider implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private LocationRetrievedCallback callback;
    private LatLng lastKnownLatLng;
    private Context context;

    public LocationProvider(Context context) {
        this.context = context;
    }

    public synchronized void buildGoogleApiClient(LocationRetrievedCallback callback) {
        this.callback = callback;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void connect() {
        googleApiClient.connect();
    }

    public void disconnect() {
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    public void fetchLocation() {
        if (!googleApiClient.isConnected()) {
            return;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (lastKnownLocation != null) {

                lastKnownLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                if (callback != null) {
                    callback.onLocationRetrieved(lastKnownLatLng);
                }
            }
        } else {
            if (callback != null) {
                callback.requestPermission();
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        fetchLocation();
    }

    public LatLng getLastKnownLatLng() {
        return lastKnownLatLng;
    }

    public boolean hasLastKnownLocation(){
        return lastKnownLatLng!=null;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public interface LocationRetrievedCallback {
        void onLocationRetrieved(LatLng latLng);
        void requestPermission();
    }
}
