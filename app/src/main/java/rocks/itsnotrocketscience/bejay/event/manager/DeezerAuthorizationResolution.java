package rocks.itsnotrocketscience.bejay.event.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by nemi on 02/05/16.
 */
public class DeezerAuthorizationResolution implements Resolution{
    private static final String[] PERMISSIONS = new String[] {
            Permissions.BASIC_ACCESS,
            Permissions.MANAGE_LIBRARY,
            Permissions.LISTENING_HISTORY };

    private final DeezerConnect deezerConnect;
    private final SessionStore sessionStore;

    public DeezerAuthorizationResolution(DeezerConnect deezerConnect, SessionStore sessionStore) {
        this.deezerConnect = deezerConnect;
        this.sessionStore = sessionStore;
    }

    @Override
    public Observable<ResolutionResult> resolve(Activity activity) {
        return Observable.create(new Observable.OnSubscribe<ResolutionResult>() {
            @Override
            public void call(Subscriber<? super ResolutionResult> subscriber) {
                if(!subscriber.isUnsubscribed()) {
                    AuthorizationSubscription s = new AuthorizationSubscription(subscriber,
                            new AuthorizationCompletedListener(activity, sessionStore, deezerConnect));
                    deezerConnect.authorize(activity, PERMISSIONS, s);
                    subscriber.add(s);
                }
            }
        });
    }


    static final class AuthorizationCompletedListener {
        private final Context context;
        private final SessionStore sessionStore;
        private final DeezerConnect deezerConnect;

        AuthorizationCompletedListener(Context context, SessionStore sessionStore, DeezerConnect deezerConnect) {
            this.context = context;
            this.sessionStore = sessionStore;
            this.deezerConnect = deezerConnect;
        }

        void onAuthorizationComplete() {
            boolean saved = sessionStore.save(deezerConnect, context);
            System.out.println(saved);
        }
    }

    private static class AuthorizationSubscription implements Subscription, DialogListener {
        private final Subscriber<? super ResolutionResult> subscriber;
        private final AuthorizationCompletedListener authorizationCompletedListener;

        public AuthorizationSubscription(Subscriber<? super ResolutionResult> subscriber,
                                         AuthorizationCompletedListener authorizationCompletedListener) {
            this.subscriber = subscriber;
            this.authorizationCompletedListener = authorizationCompletedListener;
        }

        @Override
        public void unsubscribe() {

        }

        @Override
        public boolean isUnsubscribed() {
            return subscriber.isUnsubscribed();
        }

        @Override
        public void onComplete(Bundle bundle) {
            authorizationCompletedListener.onAuthorizationComplete();
            if(!isUnsubscribed()) {
                subscriber.onNext(ResolutionResult.SUCCESS);
                subscriber.onCompleted();
            }
        }

        @Override
        public void onCancel() {
            if(!isUnsubscribed()) {
                subscriber.onNext(ResolutionResult.CANCELLED);
                subscriber.onCompleted();
            }
        }

        @Override
        public void onException(Exception e) {
            if(!isUnsubscribed()) {
                subscriber.onNext(ResolutionResult.ERROR);
                subscriber.onCompleted();
            }
        }
    }
}
