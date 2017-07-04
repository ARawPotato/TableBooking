package me.ivanfenenko.tablebooking.tables;

import java.util.List;

import javax.inject.Inject;

import me.ivanfenenko.tablebooking.api.ApiService;
import me.ivanfenenko.tablebooking.common.BasePresenter;
import me.ivanfenenko.tablebooking.common.SchedulerProviderInterface;
import me.ivanfenenko.tablebooking.database.AppPreferencesManager;
import me.ivanfenenko.tablebooking.model.Customer;
import me.ivanfenenko.tablebooking.model.Table;
import timber.log.Timber;

/**
 * Created by klickrent on 29.06.17.
 */

public class TableBookingPresenter extends BasePresenter {

    protected ApiService apiService;
    protected AppPreferencesManager preferencesManager;
    protected SchedulerProviderInterface schedulerProvider;

    protected TableRepository tableRepository;

    protected Table tableToBook;

    protected Customer customer;
    protected List<Table> tables;
    protected TableBookingView view;

    @Inject public TableBookingPresenter(ApiService apiService,
                                         AppPreferencesManager preferencesManager,
                                         SchedulerProviderInterface schedulerProvider) {
        this.apiService = apiService;
        this.preferencesManager = preferencesManager;
        this.schedulerProvider = schedulerProvider;

        tableRepository = new TableRepository();
    }

    public void setView(TableBookingView view) {
        this.view = view;
    }

    public void triggerLoading() {
        view.showProgress();
        addSubscription(tableRepository
                .getTables(apiService, preferencesManager)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .doOnNext(view::displayData)
                .subscribe(t -> tables = t, (e) -> {
                    view.displayError(e.getMessage());
                    Timber.e(e.getMessage());
                }));
    }

    public void bookTable() {
        if (tableToBook != null) {
            tableToBook.isAvailable = false;
            view.displayData(tables);
            addSubscription(preferencesManager
                    .saveBookings(tables)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.mainThread())
                    .subscribe(d -> view.tableBooked(), e -> Timber.e(e.getMessage())));
        }
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        view.displayCustomer(customer);
    }

    public void onTableSelected(Table t) {
        tableToBook = t;
        if (t.isAvailable) {
            view.showBookDialog(t);
        } else {
            view.showDeleteBookingDialog(t);
        }
    }
}
