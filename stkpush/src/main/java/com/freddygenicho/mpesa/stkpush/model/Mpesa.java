package com.freddygenicho.mpesa.stkpush.model;

import android.util.Base64;

import com.freddygenicho.mpesa.stkpush.api.RetroClient;
import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse;
import com.freddygenicho.mpesa.stkpush.interfaces.STKListener;
import com.freddygenicho.mpesa.stkpush.interfaces.STKQueryListener;
import com.freddygenicho.mpesa.stkpush.interfaces.TokenListener;

import java.io.UnsupportedEncodingException;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */

public class Mpesa {

    private CompositeSubscription mCompositeSubscription;
    private String consumerKey;
    private String consumerSecret;
    private boolean isProduction;

    public Mpesa(String consumerKey, String consumerSecret, boolean isProduction) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.isProduction = isProduction;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    /**
     * returns mpesa access token
     *
     * @param tokenListener - callback listener
     */
    public void getToken(final TokenListener tokenListener) throws UnsupportedEncodingException {
        mCompositeSubscription.add(RetroClient.getApiService(isProduction).generateAccessToken(getAuth())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        tokenListener.OnError(e);
                    }

                    @Override
                    public void onNext(Token token) {
                        tokenListener.onToken(token);
                    }
                }));
    }

    /**
     * generate mpesa bearer key
     *
     * @return - encoded auth string
     * @throws UnsupportedEncodingException - exception
     */
    private String getAuth() throws UnsupportedEncodingException {
        String consumerKeySecret = consumerKey + ":" + consumerSecret;
        byte[] bytes = consumerKeySecret.getBytes("ISO-8859-1");
        return "Basic " + Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    /**
     * start mpesa push stk
     *
     * @param token       - access token from mpesa
     * @param stkPush     - stk push object
     * @param stkListener - callback method
     */
    public void startStkPush(Token token, STKPush stkPush, final STKListener stkListener) {
        String authorization = "Bearer " + token.getAccessToken();
        mCompositeSubscription.add(RetroClient.getApiService(isProduction).stkPush(authorization, stkPush)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<STKPushResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stkListener.onError(e);
                    }

                    @Override
                    public void onNext(STKPushResponse stkPushResponse) {
                        stkListener.onResponse(stkPushResponse);
                    }
                }));
    }

    public void stkPushQuery(Token token, STKQuery stkQuery, final STKQueryListener stkQueryListener) {
        String authorization = "Bearer " + token.getAccessToken();
        mCompositeSubscription.add(RetroClient.getApiService(isProduction).stkPushQuery(authorization, stkQuery)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<STKPushResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        stkQueryListener.onError(e);
                    }

                    @Override
                    public void onNext(STKPushResponse stkPushResponse) {
                        stkQueryListener.onResponse(stkPushResponse);
                    }
                }));
    }
}
