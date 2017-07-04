package me.ivanfenenko.tablebooking.common;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by klickrent on 29.06.17.
 */

public class SchedulerProvider implements SchedulerProviderInterface {
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }
}
