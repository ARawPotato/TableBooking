package me.ivanfenenko.tablebooking;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.ivanfenenko.tablebooking.common.SchedulerProvider;
import me.ivanfenenko.tablebooking.common.SchedulerProviderInterface;
import me.ivanfenenko.tablebooking.database.AppPreferencesManager;

/**
 * Created by klickrent on 21.04.17.
 */
@Module
public class ApplicationModule {

    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    SchedulerProviderInterface provideSchedulers() {
        return new SchedulerProvider();
    }

    @Provides
    AppPreferencesManager providePreferencesManager(Context context) {
        return new AppPreferencesManager(context);
    }

}
