package me.ivanfenenko.tablebooking.common;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by klickrent on 29.06.17.
 */

public class BasePresenter {

    protected CompositeSubscription compositeSubscription;

    public BasePresenter() {
        compositeSubscription = new CompositeSubscription();
    }

    public void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    public void destroy() {
        compositeSubscription.unsubscribe();
    }

}