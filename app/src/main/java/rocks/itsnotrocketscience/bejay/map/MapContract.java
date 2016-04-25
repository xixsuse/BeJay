package rocks.itsnotrocketscience.bejay.map;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by sirfunkenstine on 23/04/16.
 */
public class MapContract  {

    interface MapView {
        void setGoogleMap(GoogleMap googleMap);
        void moveToCurrentLocation(LatLng latLng);
        void requestPermission();
        Marker addMarker(MarkerOptions party);
    }

    public interface MapPresenter extends OnMapReadyCallback , GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
        void onViewAttached(MapView view);
        void onViewDetached();
        void onDestroy();
        void finish(MapActivity mapActivity);
    }
}