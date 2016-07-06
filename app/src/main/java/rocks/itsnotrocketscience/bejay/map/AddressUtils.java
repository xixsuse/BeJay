package rocks.itsnotrocketscience.bejay.map;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddressUtils {
    private final String TAG = this.getClass().getSimpleName();
    private String errorMessage = null;

    public List<Address> getAddressList(Geocoder geocoder, Location location) {
        List<Address> addresses = null;

        try {
            addresses = getAddressesFromLocation(geocoder, location);
        } catch (IOException ioException) {
            errorMessage = "Service Not Available";
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = "invalid lat lng";
        }
        return addresses;
    }

    public List<Address> getAddressesFromLocation(Geocoder geocoder, Location location) throws IOException {
        return geocoder.getFromLocation(
                location.getLatitude(),
                location.getLongitude(),
                1);
    }

    protected ArrayList<String> getAddress(List<Address> addresses) {
        Address address = addresses.get(0);
        ArrayList<String> addressFragments = new ArrayList<>();

        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
            addressFragments.add(address.getAddressLine(i));
        }
        return addressFragments;
    }

    public boolean hasError() {
        return errorMessage != null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
