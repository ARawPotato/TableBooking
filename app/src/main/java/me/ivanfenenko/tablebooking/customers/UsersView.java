package me.ivanfenenko.tablebooking.customers;

import java.util.List;

import me.ivanfenenko.tablebooking.model.Customer;

/**
 * Created by klickrent on 02.07.17.
 */

public interface UsersView {

    void showProgress();

    void displayData(List<Customer> departures);
    void displayError(String error);

}
