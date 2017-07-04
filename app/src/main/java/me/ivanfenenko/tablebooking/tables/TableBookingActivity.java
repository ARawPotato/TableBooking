package me.ivanfenenko.tablebooking.tables;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.ivanfenenko.tablebooking.App;
import me.ivanfenenko.tablebooking.R;
import me.ivanfenenko.tablebooking.adapter.STATE;
import me.ivanfenenko.tablebooking.adapter.TableAdapter;
import me.ivanfenenko.tablebooking.common.view.DalogHelper;
import me.ivanfenenko.tablebooking.model.Customer;
import me.ivanfenenko.tablebooking.model.Table;

public class TableBookingActivity extends AppCompatActivity implements TableBookingView {

    public static final int REQUEST_CODE = 3411;

    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.customerName) TextView customerName;

    @Inject TableBookingPresenter presenter;
    @Inject TableAdapter adapter;

    public static void launchActivity(Activity activity, Customer customer) {
        Intent intent = new Intent(activity, TableBookingActivity.class);
        intent.putExtra(Customer.class.getName(), customer);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ((App) getApplication()).getApplicationComponent().inject(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);

        presenter.setView(this);
        presenter.triggerLoading();
        presenter.setCustomer((Customer) getIntent().getSerializableExtra(Customer.class.getName()));

        adapter.setItemClickListener((t) -> presenter.onTableSelected(t));
        adapter.setonErrorClickListener((v) -> presenter.triggerLoading());
    }

    @Override
    public void showProgress() {
        adapter.setState(STATE.PROGRESS);
    }

    @Override
    public void displayData(List<Table> departures) {
        adapter.setData(departures);
    }

    @Override
    public void displayError(String error) {
        adapter.setState(STATE.ERROR);
    }

    @Override
    public void showBookDialog(Table t) {
        DalogHelper.getBookDialog(this, t, (d, w) -> presenter.bookTable()).show();
    }

    @Override
    public void showDeleteBookingDialog(Table t) {
        DalogHelper.getTableUnavailableDialog(this, t).show();
    }

    @Override
    public void displayCustomer(Customer customer) {
        customerName.setText(String.format("%s %s", customer.customerFirstName, customer.customerLastName));
    }

    @Override
    public void tableBooked() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
