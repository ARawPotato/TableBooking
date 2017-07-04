package me.ivanfenenko.tablebooking;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import javax.inject.Inject;

import me.ivanfenenko.tablebooking.common.SchedulerProviderInterface;
import me.ivanfenenko.tablebooking.database.AppPreferencesManager;
import me.ivanfenenko.tablebooking.model.Table;
import me.ivanfenenko.tablebooking.tables.TableRepository;

import static me.ivanfenenko.tablebooking.tables.TableBookingActivity.REQUEST_CODE;

public class TableService extends IntentService {

    @Inject AppPreferencesManager appPreferencesManager;
    @Inject SchedulerProviderInterface schedulerProvider;
    @Inject TableRepository tableRepository;

    public TableService() {
        super("TableService");
    }

    public static void registerAlarm(Context context) {
        Intent intent = new Intent(context, BookingReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context,REQUEST_CODE, intent, 0);

        long firstTime = SystemClock.elapsedRealtime() + 600000;
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 600000, sender);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ((App) getApplication()).getApplicationComponent().inject(this);

            tableRepository
                    .getTablesFromStorage(appPreferencesManager)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.mainThread())
                    .map(tables -> {
                        for (Table t : tables) {
                            t.isAvailable = true;
                        }
                        return tables;
                    })
                    .switchMap(appPreferencesManager::saveBookings)
                    .subscribe();
        }
    }

}
