package com.freddygenicho.mpesa.stkpush.api.response

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.freddygenicho.mpesa.stkpush.api.response.CallbackMetadata
import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse
import com.google.gson.Gson

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */
class STKPushResponse {
    @SerializedName("MerchantRequestID")
    @Expose
    var merchantRequestID: String? = null

    @SerializedName("CheckoutRequestID")
    @Expose
    var checkoutRequestID: String? = null

    @SerializedName("ResultCode")
    @Expose
    var resultCode: Int? = null

    @SerializedName("ResultDesc")
    @Expose
    var resultDesc: String? = null

    @SerializedName("CallbackMetadata")
    @Expose
    var callbackMetadata: CallbackMetadata? = null
    override fun toString(): String {
        return "{\"MerchantRequestID\":\"" + merchantRequestID + "\"," +
                "\"checkoutRequestID\":\"" + checkoutRequestID + "\"," +
                "\"resultCode\":\"" + resultCode + "\"," +
                "\"resultDesc\":\"" + resultDesc + "\"," +
                "\"callbackMetadata\":\"" + callbackMetadata +
                "\"}"
    }

    fun toJson(stkPushResponse: STKPushResponse?): String {
        val gson = Gson()
        return gson.toJson(stkPushResponse)
    }
}