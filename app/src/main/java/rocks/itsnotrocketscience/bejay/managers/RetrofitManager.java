package rocks.itsnotrocketscience.bejay.managers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rocks.itsnotrocketscience.bejay.api.ApiConstants;
import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.AuthCredentials;
import rocks.itsnotrocketscience.bejay.api.retrofit.CheckInUserToEvent;
import rocks.itsnotrocketscience.bejay.api.retrofit.CreateUser;
import rocks.itsnotrocketscience.bejay.api.retrofit.GetEvent;
import rocks.itsnotrocketscience.bejay.api.retrofit.GetEvents;
import rocks.itsnotrocketscience.bejay.api.retrofit.LoginUser;
import rocks.itsnotrocketscience.bejay.base.AppApplication;
import rocks.itsnotrocketscience.bejay.models.CmsUser;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;
import rocks.itsnotrocketscience.bejay.models.Token;

/**
 * Created by centralstation on 22/10/15.
 *
 */
public class RetrofitManager {

    RestAdapter restAdapter;
    AppApplication application;
    Context mContext;
    private static RetrofitManager manager = null;
    private RetrofitManager(Context context) {

        application = (AppApplication)context.getApplicationContext();
        mContext=context;
        restAdapter = new RestAdapter.Builder()
                .setRequestInterceptor(application.getAccountManager().getAuthTokenInterceptor())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ApiConstants.API)
                .build();
    }

    public static RetrofitManager get(Context context){
        if (manager==null){
            return new RetrofitManager(context);
        }
        else{
            return manager;
        }
    }

    public void checkinUser(CheckinListener listener, final int id) {

        CheckInUserToEvent checkin = restAdapter.create(CheckInUserToEvent.class);
        checkin.checkIn(id, new Callback<Event>() {
            @Override
            public void success(Event event, Response response) {
                listener.onCheckedIn(id, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onCheckedIn(id, error);
            }
        });
    }

    public void getEventListFeed(EventListListener listener) {
        GetEvents events = restAdapter.create(GetEvents.class);

        events.getFeed("events", new Callback<ArrayList<Event>>() {
            @Override
            public void success(ArrayList<Event> eventList, Response response) {
                listener.onEventFeedLoaded(eventList, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onEventFeedLoaded(null, error);

            }
        });
    }

    public void checkoutUser(CheckoutListener listener, int id) {
        CheckInUserToEvent checkin = restAdapter.create(CheckInUserToEvent.class);
        checkin.checkOut(id, new Callback<Event>() {
            @Override
            public void success(Event event, Response response) {
                application.getAccountManager().clearCheckin();
                listener.onCheckedOut(id, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onCheckedOut(id, error);
            }
        });
    }

    public void getEventFeed(EventListener listener,int id) {
        GetEvent event = restAdapter.create(GetEvent.class);

        event.getFeed(id, new Callback<Event>() {
            @Override
            public void success(Event eventList, Response response) {
                listener.onEventLoaded(eventList, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onEventLoaded(null, error);
            }
        });
    }

    public void loginUser( AuthCredentials auth, LoginListener listener){
        LoginUser loginUser = restAdapter.create(LoginUser.class);

        loginUser.loginUser(ApiConstants.TOKEN, auth, new Callback<Token>() {
            @Override
            public void success(Token token, Response response) {
                listener.onLoggedIn(token, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onLoggedIn(null, error);
            }
        });

    }

    public void registerUser(CmsUser user, RegisterListener listener){
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(ApiConstants.API).build();
        CreateUser createUser = restAdapter.create(CreateUser.class);
        createUser.createUser(user, new Callback<CmsUser>() {
            @Override
            public void success(CmsUser user, Response response) {
                listener.onRegistered(user, null);
            }

            @Override
            public void failure(RetrofitError error) {
                listener.onRegistered(null, error);
            }
        });
    }

    public interface EventListListener{
        void onEventFeedLoaded( ArrayList<Event> list, RetrofitError error);
    }
    public interface CheckoutListener{
        void onCheckedOut(int id, RetrofitError error);
    }
    public interface CheckinListener{
        void onCheckedIn(int id, RetrofitError error);
    }
    public interface EventListener{
        void onEventLoaded(Event list, RetrofitError error);
    }
    public interface LoginListener{
        void onLoggedIn(Token token, RetrofitError error);
    }
    public interface RegisterListener{
        void onRegistered(CmsUser user, RetrofitError error);
    }
}
