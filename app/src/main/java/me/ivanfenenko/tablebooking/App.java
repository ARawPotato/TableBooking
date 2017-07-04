package me.ivanfenenko.tablebooking;

import android.app.Application;

import me.ivanfenenko.tablebooking.api.ApiModule;
import timber.log.Timber;

/**
 * Created by klickrent on 20.04.17.
 */

public class App extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        ApplicationModule applicationModule = new ApplicationModule(this);
        ApiModule apiModule = new ApiModule();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(applicationModule)
                .apiModule(apiModule)
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
