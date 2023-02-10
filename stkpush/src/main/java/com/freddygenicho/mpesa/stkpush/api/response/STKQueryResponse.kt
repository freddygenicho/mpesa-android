package com.freddygenicho.mpesa.stkpush.api.response

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */
class STKQueryResponse {
    @SerializedName("ResponseCode")
    @Expose
    var responseCode: String? = null

    @SerializedName("ResponseDescription")
    @Expose
    var responseDescription: String? = null

    @SerializedName("MerchantRequestID")
    @Expose
    var merchantRequestID: String? = null

    @SerializedName("CheckoutRequestID")
    @Expose
    var checkoutRequestID: String? = null

    @SerializedName("ResultCode")
    @Expose
    var resultCode: String? = null

    @SerializedName("ResultDesc")
    @Expose
    var resultDesc: String? = null
}