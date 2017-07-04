package me.ivanfenenko.tablebooking.tables;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import me.ivanfenenko.tablebooking.TestSchedulersProvider;
import me.ivanfenenko.tablebooking.api.ApiService;
import me.ivanfenenko.tablebooking.database.AppPreferencesManager;
import me.ivanfenenko.tablebooking.model.Customer;
import me.ivanfenenko.tablebooking.model.Table;
import rx.Observable;

/**
 * Created by klickrent on 30.06.17.
 */

public class MainPresenterTest {

    @Mock ApiService apiService;
    @Mock AppPreferencesManager appPreferencesManager;
    @Mock TableBookingView mainView;

    TestSchedulersProvider schedulerProvider = new TestSchedulersProvider();
    TableBookingPresenter mainPresenter;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);

        mainPresenter = new TableBookingPresenter(apiService, appPreferencesManager, schedulerProvider);
        mainPresenter.setView(mainView);

        mainPresenter.tableRepository = Mockito.mock(TableRepository.class);
    }

    @Test
    public void testLoading_success() {
        List<Table> testData = new ArrayList<>();
        testData.add(new Table());

        Mockito
                .when(mainPresenter.tableRepository.getTables(apiService, appPreferencesManager))
                .thenReturn(Observable.just(testData));

        mainPresenter.triggerLoading();

        Mockito.verify(mainView).displayData(testData);
    }

    @Test
    public void testLoading_error() {
        List<Table> testData = new ArrayList<>();
        testData.add(new Table());

        Mockito
                .when(mainPresenter.tableRepository.getTables(apiService, appPreferencesManager))
                .thenReturn(Observable.error(new NullPointerException()));

        mainPresenter.triggerLoading();

        Mockito.verify(mainView).displayError(Mockito.anyString());
    }

    @Test
    public void bookTable_success() {
        List<Table> testData = new ArrayList<>();
        testData.add(new Table());

        mainPresenter.tables = testData;
        mainPresenter.tableToBook = new Table();

        Mockito
                .when(appPreferencesManager.saveBookings(mainPresenter.tables))
                .thenReturn(Observable.just(testData));

        mainPresenter.bookTable();

        Assert.assertFalse(mainPresenter.tableToBook.isAvailable);
    }

    @Test
    public void setCustomer_success() {
        Customer customer = new Customer();

        mainPresenter.setCustomer(customer);

        Assert.assertEquals(mainPresenter.customer, customer);
        Mockito.verify(mainView).displayCustomer(customer);
    }

    @Test
    public void onTableSelected_available() {
        Table table = new Table();
        table.isAvailable = true;

        mainPresenter.onTableSelected(table);

        Mockito.verify(mainView).showBookDialog(table);
    }

    @Test
    public void onTableSelected_notavailable() {
        Table table = new Table();
        table.isAvailable = false;

        mainPresenter.onTableSelected(table);

        Mockito.verify(mainView).showDeleteBookingDialog(table);
    }

}
