package com.freddygenicho.mpesa.stkpush.api.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */

public class STKPushResponse {

    @SerializedName("MerchantRequestID")
    @Expose
    private String merchantRequestID;
    @SerializedName("CheckoutRequestID")
    @Expose
    private String checkoutRequestID;
    @SerializedName("ResultCode")
    @Expose
    private Integer resultCode;
    @SerializedName("ResultDesc")
    @Expose
    private String resultDesc;
    @SerializedName("CallbackMetadata")
    @Expose
    private CallbackMetadata callbackMetadata;

    public String getMerchantRequestID() {
        return merchantRequestID;
    }

    public void setMerchantRequestID(String merchantRequestID) {
        this.merchantRequestID = merchantRequestID;
    }

    public String getCheckoutRequestID() {
        return checkoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        this.checkoutRequestID = checkoutRequestID;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public CallbackMetadata getCallbackMetadata() {
        return callbackMetadata;
    }

    public void setCallbackMetadata(CallbackMetadata callbackMetadata) {
        this.callbackMetadata = callbackMetadata;
    }

    public String toString() {
        return "{\"MerchantRequestID\":\"" + merchantRequestID + "\"," +
                "\"checkoutRequestID\":\"" + checkoutRequestID + "\"," +
                "\"resultCode\":\"" + resultCode + "\"," +
                "\"resultDesc\":\"" + resultDesc + "\"," +
                "\"callbackMetadata\":\"" + callbackMetadata +
                "\"}";
    }

    public String toJson(STKPushResponse stkPushResponse) {
        Gson gson = new Gson();
        return gson.toJson(stkPushResponse);
    }

}
