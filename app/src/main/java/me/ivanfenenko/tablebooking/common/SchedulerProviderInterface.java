package me.ivanfenenko.tablebooking.common;

import rx.Scheduler;

/**
 * Created by klickrent on 29.06.17.
 */

public interface SchedulerProviderInterface {

    Scheduler io();

    Scheduler mainThread();
}
