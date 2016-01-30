package rocks.itsnotrocketscience.bejay.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rocks.itsnotrocketscience.bejay.api.ApiManager;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.dao.EventsDao;
import rocks.itsnotrocketscience.bejay.db.ModelDbHelper;
import rocks.itsnotrocketscience.bejay.event.list.EventListContract;
import rocks.itsnotrocketscience.bejay.event.list.EventListPresenterImpl;
import rocks.itsnotrocketscience.bejay.login.LoginContract;
import rocks.itsnotrocketscience.bejay.login.LoginPresenterImpl;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;

/**
 * Created by lduf0001 on 21/12/15.
 */
@Module
public class NetModule {

    String mBaseUrl;

    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    AccountManager providesAccountManager(SharedPreferences sharedPreferences) {
        return new AccountManager(sharedPreferences);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    ApiManager provideRetrofitManager(Application application, AccountManager accountManager) {
        return new ApiManager(application,accountManager);
    }

    @Provides @Singleton
    Events providesEventsApi(ApiManager apiManager) {
        return apiManager.events();
    }

    @Provides EventListContract.EventListPresenter providesEventListPresenter(EventsDao eventsDao, Events networkEvents, AccountManager accountManager) {
        return new EventListPresenterImpl(eventsDao, networkEvents, accountManager);
    }

    @Provides
    LoginContract.LoginPresenter providesLoginPresenter(Context context,SharedPreferences sharedPreferences) {
        return new LoginPresenterImpl(context, sharedPreferences);
    }


    @Provides @Singleton  SqlBrite providesSqlBrite() {
        return SqlBrite.create();
    }

    @Provides @Singleton  BriteDatabase providesDb(SqlBrite sqlBrite, Context context) {
        return sqlBrite.wrapDatabaseHelper(new ModelDbHelper(context));
    }
}
