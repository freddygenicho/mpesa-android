package com.freddygenicho.mpesa.stkpush.api.response;

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallbackMetadata {

    @SerializedName("Item")
    @Expose
    private List<Item> item = null;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

}
