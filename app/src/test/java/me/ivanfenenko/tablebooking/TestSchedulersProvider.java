package me.ivanfenenko.tablebooking;

import me.ivanfenenko.tablebooking.common.SchedulerProviderInterface;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by klickrent on 24.04.17.
 */

public class TestSchedulersProvider implements SchedulerProviderInterface {

    @Override
    public Scheduler io() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler mainThread() {
        return Schedulers.immediate();
    }

}
