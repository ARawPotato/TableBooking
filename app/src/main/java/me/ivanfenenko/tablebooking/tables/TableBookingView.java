package me.ivanfenenko.tablebooking.tables;

import java.util.List;

import me.ivanfenenko.tablebooking.model.Customer;
import me.ivanfenenko.tablebooking.model.Table;

/**
 * Created by klickrent on 29.06.17.
 */

public interface TableBookingView {

    void showProgress();

    void displayData(List<Table> departures);
    void displayError(String error);

    void tableBooked();

    void showBookDialog(Table t);

    void showDeleteBookingDialog(Table t);

    void displayCustomer(Customer customer);
}
