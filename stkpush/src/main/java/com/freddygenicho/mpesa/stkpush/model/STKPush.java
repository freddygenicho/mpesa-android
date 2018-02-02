package com.freddygenicho.mpesa.stkpush.model;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */

public class STKPush {

    @SerializedName("BusinessShortCode")
    @Expose
    private String businessShortCode;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Timestamp")
    @Expose
    private String timestamp;
    @SerializedName("TransactionType")
    @Expose
    private String transactionType;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("PartyA")
    @Expose
    private String partyA;
    @SerializedName("PartyB")
    @Expose
    private String partyB;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("CallBackURL")
    @Expose
    private String callBackURL;
    @SerializedName("AccountReference")
    @Expose
    private String accountReference;
    @SerializedName("TransactionDesc")
    @Expose
    private String transactionDesc;


    public STKPush(String businessShortCode, String password, String timestamp, String transactionType, String amount, String partyA, String partyB, String phoneNumber, String callBackURL, String accountReference, String transactionDesc) {
        this.businessShortCode = businessShortCode;
        this.password = password;
        this.timestamp = timestamp;
        this.transactionType = transactionType;
        this.amount = amount;
        this.partyA = partyA;
        this.partyB = partyB;
        this.phoneNumber = phoneNumber;
        this.callBackURL = callBackURL;
        this.accountReference = accountReference;
        this.transactionDesc = transactionDesc;
    }

    public String toString() {
        return "{\"BusinessShortCode\":\"" + businessShortCode + "\"," +
                "\"Password\":\"" + password + "\"," +
                "\"Timestamp\":\"" + timestamp + "\"," +
                "\"TransactionType\":\"" + transactionType + "\"," +
                "\"Amount\":\"" + amount + "\"," +
                "\"PartyA\":\"" + partyA + "\"," +
                "\"PartyB\":\"" + partyB + "\"," +
                "\"PhoneNumber\":\"" + phoneNumber + "\"," +
                "\"CallBackURL\":\"" + callBackURL + "\"," +
                "\"AccountReference\":\"" + accountReference + "\"," +
                "\"TransactionDesc\":\"" + transactionDesc +
                "\"}";
    }

    public String toJson(STKPush stkPush) {
        Gson gson = new Gson();
        return gson.toJson(stkPush);
    }

    public static String sanitizePhoneNumber(String phone) {

        if (phone.equals("")) {
            return "";
        }

        if (phone.length() < 11 & phone.startsWith("0")) {
            String p = phone.replaceFirst("^0", "254");
            return p;
        }
        if (phone.length() == 13 && phone.startsWith("+")) {
            String p = phone.replaceFirst("^+", "");
            return p;
        }
        return phone;
    }

    public static String getPassword(String businessShortCode, String passkey, String timestamp) {
        String str = businessShortCode + passkey + timestamp;
        //encode the password to Base64
        return Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
    }

    public static String getTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
    }

}
