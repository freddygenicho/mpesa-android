package com.freddygenicho.mpesa.stkpush.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */
class STKQuery {
    @SerializedName("BusinessShortCode")
    @Expose
    var businessShortCode: String? = null

    @SerializedName("Password")
    @Expose
    var password: String? = null

    @SerializedName("Timestamp")
    @Expose
    var timestamp: String? = null

    @SerializedName("CheckoutRequestID")
    @Expose
    var checkoutRequestID: String? = null
}