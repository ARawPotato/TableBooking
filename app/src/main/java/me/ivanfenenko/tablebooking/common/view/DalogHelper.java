package me.ivanfenenko.tablebooking.common.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import me.ivanfenenko.tablebooking.model.Table;

/**
 * Created by klickrent on 01.07.17.
 */

public class DalogHelper {

    public static Dialog getBookDialog(Context c, Table t, DialogInterface.OnClickListener d) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(String.format("Create booking table #%d", t.tableId));
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", d);
        builder.setNegativeButton("No", null);
        return builder.create();
    }

    public static Dialog getTableUnavailableDialog(Context c, Table t) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(String.format("Table #%d is unavailable", t.tableId));
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        return builder.create();
    }

}
