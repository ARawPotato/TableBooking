package me.ivanfenenko.tablebooking.api;

import java.util.List;

import me.ivanfenenko.tablebooking.model.Customer;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by klickrent on 29.06.17.
 */

public interface ApiService {

    @GET("/quandoo-assessment/table-map.json")
    Observable<List<Boolean>> getTableMap();

    @GET("/quandoo-assessment/customer-list.json")
    Observable<List<Customer>> getCustomerList();

}
