package me.ivanfenenko.tablebooking.customers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.ivanfenenko.tablebooking.App;
import me.ivanfenenko.tablebooking.R;
import me.ivanfenenko.tablebooking.adapter.CustomerAdapter;
import me.ivanfenenko.tablebooking.adapter.STATE;
import me.ivanfenenko.tablebooking.model.Customer;
import me.ivanfenenko.tablebooking.tables.TableBookingActivity;

public class UserListActivity extends AppCompatActivity implements UsersView {

    @Inject CustomerAdapter adapter;
    @Inject UsersPresenter presenter;

    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ButterKnife.bind(this);
        ((App) getApplication()).getApplicationComponent().inject(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        presenter.setView(this);
        presenter.triggerLoading();

        adapter.setItemClickListener((c) -> TableBookingActivity.launchActivity(this, c));
        adapter.setonErrorClickListener((v) -> presenter.triggerLoading());
    }

    @Override
    public void showProgress() {
        adapter.setState(STATE.PROGRESS);
    }

    @Override
    public void displayData(List<Customer> customerList) {
        adapter.setCustomerList(customerList);
    }

    @Override
    public void displayError(String error) {
        adapter.setState(STATE.ERROR);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

}
