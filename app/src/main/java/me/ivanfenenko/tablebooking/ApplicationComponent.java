package me.ivanfenenko.tablebooking;

import javax.inject.Singleton;

import dagger.Component;
import me.ivanfenenko.tablebooking.api.ApiModule;
import me.ivanfenenko.tablebooking.tables.TableBookingActivity;
import me.ivanfenenko.tablebooking.customers.UserListActivity;

/**
 * Created by klickrent on 21.04.17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

    void inject(TableBookingActivity activity);
    void inject(UserListActivity activity);

}
