package me.ivanfenenko.tablebooking.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by klickrent on 02.07.17.
 */

public class Customer implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("customerFirstName")
    @Expose
    public String customerFirstName;

    @SerializedName("customerLastName")
    @Expose
    public String customerLastName;
}
