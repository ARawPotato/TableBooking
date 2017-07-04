package me.ivanfenenko.tablebooking.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.ivanfenenko.tablebooking.model.Customer;
import me.ivanfenenko.tablebooking.model.Table;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by klickrent on 02.07.17.
 */

public class AppPreferencesManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public String KEY_LIST_CUSTOMERS = "KEY_LIST_CUSTOMERS";
    public String KEY_LIST_BOOKINGS = "KEY_LIST_BOOKINGS";

    public AppPreferencesManager(Context context) {
        preferences = context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
    }

    public Observable<List<Boolean>> getBookings() {
        return Observable.create((Subscriber<? super List<Boolean>> sub) -> {
            try {
                String json = preferences.getString(KEY_LIST_BOOKINGS, "");
                Type type = new TypeToken<ArrayList<Boolean>>(){}.getType();
                ArrayList<Boolean> bookings = new Gson().fromJson(json, type);
                sub.onNext(bookings != null ? bookings : new ArrayList<Boolean>());
            } catch (Exception ex) {
                sub.onError(ex);
            } finally {
                sub.onCompleted();
            }
        });
    }

    public Observable<List<Table>> saveBookings(List<Table> tables) {
        return Observable.create((Subscriber<? super List<Table>> sub) -> {
            ArrayList<Boolean> bookings = new ArrayList<>();
            for (Table t : tables) {
                bookings.add(t.isAvailable);
            }
            Gson gson = new Gson();
            String json = gson.toJson(bookings);
            editor = preferences.edit();
            editor.putString(KEY_LIST_BOOKINGS, json);
            editor.commit();
            sub.onNext(tables);
            sub.onCompleted();
        });
    }

    public Observable<List<Customer>> getCustomers() {
        return Observable.create((Subscriber<? super List<Customer>> sub) -> {
            try {
                String json = preferences.getString(KEY_LIST_CUSTOMERS, "");
                Type type = new TypeToken<ArrayList<Customer>>(){}.getType();
                ArrayList<Customer> bookings = new Gson().fromJson(json, type);
                sub.onNext(bookings != null ? bookings : new ArrayList<Customer>());
            } catch (Exception ex) {
                sub.onError(ex);
            } finally {
                sub.onCompleted();
            }
        });
    }

    public Observable<List<Customer>> saveCustomers(List<Customer> customers) {
        return Observable.create((Subscriber<? super List<Customer>> sub) -> {
            Gson gson = new Gson();
            String json = gson.toJson(customers);
            editor = preferences.edit();
            editor.putString(KEY_LIST_CUSTOMERS, json);
            editor.commit();
            sub.onNext(customers);
            sub.onCompleted();
        });
    }

}
