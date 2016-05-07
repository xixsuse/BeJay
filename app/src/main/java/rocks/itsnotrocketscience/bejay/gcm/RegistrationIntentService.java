package rocks.itsnotrocketscience.bejay.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;

import javax.inject.Inject;

import retrofit.client.Response;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.retrofit.GcmRegistration;
import rocks.itsnotrocketscience.bejay.api.retrofit.GcmRegistrationDetails;
import rocks.itsnotrocketscience.bejay.base.AppApplication;
import rocks.itsnotrocketscience.bejay.managers.ServiceFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    public static final String GCM_TOKEN = "GcmToken";
    @Inject
    SharedPreferences sharedPreferences;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((AppApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getResources().getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            retrieveUserDetails(token);
            sharedPreferences.edit().putString(GCM_TOKEN, token).apply();
        } catch (Exception e) {
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
    }

    public void retrieveUserDetails(String token) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = new GraphRequest(accessToken, "/me");
        Observable.just(request.executeAndWait())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(graphResponse -> {
                    try {
                        return new GcmRegistrationDetails(
                                graphResponse.getJSONObject().getString("id"), token, graphResponse.getJSONObject().getString("name")

                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .subscribe(new Subscriber<GcmRegistrationDetails>() {
                    @Override public void onCompleted() {
                    }

                    @Override public void onError(Throwable e) {
                    }

                    @Override public void onNext(GcmRegistrationDetails details) {
                        sendRegistrationToServer(details);
                    }
                });

    }

    private void sendRegistrationToServer(GcmRegistrationDetails details) {

        GcmRegistration register = ServiceFactory.createRetrofitServiceNoAuth(GcmRegistration.class);
        register.register(details)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override public void onCompleted() {
                    }

                    @Override public final void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override public final void onNext(Response response) {
                        sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
                        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
                        LocalBroadcastManager.getInstance(RegistrationIntentService.this).sendBroadcast(registrationComplete);
                    }
                });
    }
}