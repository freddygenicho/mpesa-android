package com.freddygenicho.sample.mpesa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.freddygenicho.mpesa.stkpush.Mode;
import com.freddygenicho.mpesa.stkpush.model.Token;
import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse;
import com.freddygenicho.mpesa.stkpush.interfaces.STKListener;
import com.freddygenicho.mpesa.stkpush.interfaces.TokenListener;
import com.freddygenicho.mpesa.stkpush.model.Mpesa;
import com.freddygenicho.mpesa.stkpush.model.STKPush;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startMpesa(View view) {
        final Mpesa mpesa = new Mpesa(Config.CONSUMER_KEY, Config.CONSUMER_SECRET, Mode.SANDBOX);

        try {
            mpesa.getToken(new TokenListener() {
                @Override
                public void onToken(Token token) {

                    String phone_number = "";

                    STKPush stkPush = new STKPush();
                    stkPush.setBusinessShortCode(Config.BUSINESS_SHORT_CODE);
                    stkPush.setTimestamp(STKPush.getTimestamp());
                    stkPush.setTransactionType(Config.TRANSACTION_TYPE);
                    stkPush.setAmount("100");
                    stkPush.setPartyA(STKPush.sanitizePhoneNumber(phone_number));
                    stkPush.setPartyB(Config.PARTYB);
                    stkPush.setPhoneNumber(STKPush.sanitizePhoneNumber(phone_number));
                    stkPush.setCallBackURL(Config.CALLBACKURL);
                    stkPush.setAccountReference("test");
                    stkPush.setTransactionDesc("some description");

                    mpesa.startStkPush(token, stkPush, new STKListener() {
                        @Override
                        public void onResponse(STKPushResponse stkPushResponse) {
                            Log.e(TAG, "onResponse: " + stkPushResponse.toJson(stkPushResponse));
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onError: " + throwable.getMessage());
                        }
                    });

                }

                @Override
                public void OnError(Throwable throwable) {
                    Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "OnError: " + throwable.getMessage());
                }
            });
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "init: " + e.getLocalizedMessage());
        }
    }

}
