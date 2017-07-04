package me.ivanfenenko.tablebooking.customers;

import java.util.List;

import me.ivanfenenko.tablebooking.api.ApiService;
import me.ivanfenenko.tablebooking.database.AppPreferencesManager;
import me.ivanfenenko.tablebooking.model.Customer;
import rx.Observable;

/**
 * Created by klickrent on 03.07.17.
 */

public class CustomerRepository {

    public Observable<List<Customer>> getCustomers(ApiService apiService,
                                                   AppPreferencesManager preferencesManager) {
        return Observable
                .concat(
                        preferencesManager.getCustomers(),
                        getCustomersFromNetwork(apiService, preferencesManager)
                )
                .filter((d) -> !d.isEmpty())
                .firstOrDefault(null);

    }

    protected Observable<List<Customer>> getCustomersFromNetwork(ApiService apiService,
                                                                 AppPreferencesManager preferencesManager) {
        return apiService
                .getCustomerList()
                .switchMap(preferencesManager::saveCustomers);
    }

}
