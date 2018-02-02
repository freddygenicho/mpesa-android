package com.freddygenicho.mpesa.stkpush.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */

public class STKQuery {

    @SerializedName("BusinessShortCode")
    @Expose
    private String businessShortCode;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Timestamp")
    @Expose
    private String timestamp;
    @SerializedName("CheckoutRequestID")
    @Expose
    private String checkoutRequestID;

    public String getBusinessShortCode() {
        return businessShortCode;
    }

    public void setBusinessShortCode(String businessShortCode) {
        this.businessShortCode = businessShortCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCheckoutRequestID() {
        return checkoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        this.checkoutRequestID = checkoutRequestID;
    }

}