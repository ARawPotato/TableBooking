package me.ivanfenenko.tablebooking.tables;

import java.util.ArrayList;
import java.util.List;

import me.ivanfenenko.tablebooking.api.ApiService;
import me.ivanfenenko.tablebooking.database.AppPreferencesManager;
import me.ivanfenenko.tablebooking.model.Table;
import rx.Observable;

/**
 * Created by klickrent on 02.07.17.
 */

public class TableRepository {

    public Observable<List<Table>> getTables(ApiService apiService,
                                  AppPreferencesManager preferencesManager) {
        return Observable
                .concat(
                    getTablesFromStorage(preferencesManager),
                    getTablesFromNetwork(apiService, preferencesManager)
                )
                .filter((d) -> !d.isEmpty())
                .first();
    }

    protected List<Table> bookingsToTable(List<Boolean> bookings) {
        List<Table> tables = new ArrayList<>();
        for (int i = 0; i < bookings.size(); i++) {
            Boolean isAvailable = bookings.get(i);
            Table table = new Table();
            table.isAvailable = isAvailable;
            table.tableId = i;
            tables.add(table);
        }
        return tables;
    }

    protected Observable<List<Table>> getTablesFromNetwork(ApiService apiService,
                                                        AppPreferencesManager preferencesManager) {
        return apiService
                .getTableMap()
                .map(bookings -> bookingsToTable(bookings))
                .zipWith(preferencesManager.getBookings(), (List<Table> t, List<Boolean> b) -> {
                    for (int i = 0; i < t.size();  i++) {
                        if (b.size() > i) {
                            t.get(i).isAvailable = b.get(i) && t.get(i).isAvailable;
                        }
                    }
                    return t;
                })
                .switchMap(preferencesManager::saveBookings);
    }

    protected Observable<List<Table>> getTablesFromStorage(AppPreferencesManager preferencesManager) {
        return preferencesManager
                .getBookings()
                .map(this::bookingsToTable)
                .zipWith(preferencesManager.getBookings(), (List<Table> t, List<Boolean> b) -> {
                    for (int i = 0; i < t.size();  i++) {
                        if (b.size() > i) {
                            t.get(i).isAvailable = b.get(i);
                        }
                    }
                    return t;
                });
    }

}
