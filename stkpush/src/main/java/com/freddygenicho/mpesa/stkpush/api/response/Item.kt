package com.freddygenicho.mpesa.stkpush.api.response

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */
class Item {
    @SerializedName("Name")
    @Expose
    var name: String? = null

    @SerializedName("Value")
    @Expose
    var value: Int? = null
}