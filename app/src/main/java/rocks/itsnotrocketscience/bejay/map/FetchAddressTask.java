package rocks.itsnotrocketscience.bejay.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.Locale;

class FetchAddressTask implements Runnable {
    private static final String TAG = "FetchAddress";
    private final Context context;
    private Location location;
    private final AddressUtils addressUtils;
    private final MapPresenterImpl.OnAddressResolvedCallback onAddressResolvedCallback;

    public FetchAddressTask(Context context, MapPresenterImpl.OnAddressResolvedCallback onAddressResolvedCallback) {
        this.context = context;
        this.onAddressResolvedCallback = onAddressResolvedCallback;
        addressUtils = new AddressUtils();
    }

    @Override public void run() {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        handleAddressList(addressUtils.getAddressList(geocoder, location));
    }

    private void handleAddressList(List<Address> addresses) {
        if (addresses == null || addresses.size() == 0) {
            if (addressUtils.hasError()) {
                Log.e(TAG, addressUtils.getErrorMessage());
            }
            sendResult(null);
        } else {
            sendResult(TextUtils.join(System.getProperty("line.separator"), addressUtils.getAddress(addresses)));
        }
    }

    private void sendResult(String result) {
        onAddressResolvedCallback.onAddressResolved(result);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}