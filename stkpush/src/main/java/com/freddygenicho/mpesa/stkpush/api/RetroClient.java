package com.freddygenicho.mpesa.stkpush.api;

import com.freddygenicho.mpesa.stkpush.Mode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */

public class RetroClient {

    private static final String PRODUCTION_URL = "https://api.safaricom.co.ke/";
    private static final String SANDBOX_URL = "https://sandbox.safaricom.co.ke/";

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance(String url) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static MpesaApi getApiService(Mode mode) {
        String url = SANDBOX_URL;

        if (mode == Mode.PRODUCTION)
            url = PRODUCTION_URL;

        return getRetrofitInstance(url).create(MpesaApi.class);
    }

}
