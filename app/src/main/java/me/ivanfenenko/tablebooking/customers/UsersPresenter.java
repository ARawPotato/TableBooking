package me.ivanfenenko.tablebooking.customers;

import javax.inject.Inject;

import me.ivanfenenko.tablebooking.api.ApiService;
import me.ivanfenenko.tablebooking.common.BasePresenter;
import me.ivanfenenko.tablebooking.common.SchedulerProviderInterface;
import me.ivanfenenko.tablebooking.database.AppPreferencesManager;
import timber.log.Timber;

/**
 * Created by klickrent on 02.07.17.
 */

public class UsersPresenter extends BasePresenter {

    protected ApiService apiService;
    protected AppPreferencesManager preferencesManager;
    protected SchedulerProviderInterface schedulerProvider;

    protected CustomerRepository customerRepository;

    protected UsersView view;

    @Inject public UsersPresenter(ApiService apiService,
                                  AppPreferencesManager preferencesManager,
                                  SchedulerProviderInterface schedulerProvider) {
        this.apiService = apiService;
        this.preferencesManager = preferencesManager;
        this.schedulerProvider = schedulerProvider;

        customerRepository = new CustomerRepository();
    }

    public void setView(UsersView view) {
        this.view = view;
    }

    public void triggerLoading() {
        view.showProgress();
        addSubscription(customerRepository.getCustomers(apiService, preferencesManager)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(
                        view::displayData,
                        (e) -> {
                            view.displayError(e.getMessage());
                            Timber.e(e.getMessage());
                        }
                ));
    }

}
