package me.ivanfenenko.tablebooking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BookingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, TableService.class));
    }

}
