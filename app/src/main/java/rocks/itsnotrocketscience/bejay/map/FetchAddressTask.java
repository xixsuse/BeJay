package rocks.itsnotrocketscience.bejay.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.Locale;

import rocks.itsnotrocketscience.bejay.api.Constants;

public class FetchAddressTask implements Runnable {
    private static final String TAG = "FetchAddressIS";
    Handler handler;
    Location location;
    Context context;
    AddressUtils addressUtils;

    public FetchAddressTask(Context context, Handler handler) {
        this.handler = handler;
        this.context = context;
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
            sendMessage(Constants.FAILURE_RESULT, addressUtils.getErrorMessage());
        } else {
            sendMessage(Constants.SUCCESS_RESULT, TextUtils.join(System.getProperty("line.separator"), addressUtils.getAddress(addresses)));
        }
    }

    private void sendMessage(int resultCode, String errorMessage) {
        Message msg = handler.obtainMessage();
        msg.setData(getBundle(resultCode, errorMessage));
        handler.sendMessage(msg);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Bundle getBundle(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        bundle.putInt(Constants.RESULT_CODE, resultCode);
        return bundle;
    }
}