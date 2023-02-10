package com.freddygenicho.mpesa.stkpush.api

import com.freddygenicho.mpesa.stkpush.Mode
import okhttp3.OkHttpClient.Builder
import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.schedulers.Schedulers
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */
object RetroClient {
    private const val PRODUCTION_URL = "https://api.safaricom.co.ke/"
    private const val SANDBOX_URL = "https://sandbox.safaricom.co.ke/"

    /**
     * Get Retrofit Instance
     */
    private fun getRetrofitInstance(url: String): Retrofit {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()
        val rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io())
        val clientBuilder = Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)
        return Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(rxAdapter)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientBuilder.build())
            .build()
    }

    /**
     * Get API Service
     *
     * @return MpesaApi Service
     */
    fun getApiService(mode: Mode): MpesaApi {
        var url = SANDBOX_URL
        if (mode == Mode.PRODUCTION) url = PRODUCTION_URL
        return getRetrofitInstance(url).create(MpesaApi::class.java)
    }
}