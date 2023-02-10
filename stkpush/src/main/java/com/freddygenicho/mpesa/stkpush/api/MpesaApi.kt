package com.freddygenicho.mpesa.stkpush.api

import com.freddygenicho.mpesa.stkpush.model.STKPush
import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse
import com.freddygenicho.mpesa.stkpush.model.STKQuery
import com.freddygenicho.mpesa.stkpush.model.Token
import retrofit2.http.*
import rx.Observable

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */
interface MpesaApi {
    @Headers("Cache-Control: max-age=640000", "cache-control: no-cache")
    @GET("oauth/v1/generate?grant_type=client_credentials")
    fun generateAccessToken(@Header("Authorization") authorization: String?): Observable<Token?>?

    @Headers("content-type: application/json")
    @POST("mpesa/stkpush/v1/processrequest")
    fun stkPush(
        @Header("Authorization") authorization: String?,
        @Body stkPush: STKPush?
    ): Observable<STKPushResponse?>?

    @Headers("content-type: application/json")
    @POST("mpesa/stkpush/v1/query")
    fun stkPushQuery(
        @Header("Authorization") authorization: String?,
        @Body stkQuery: STKQuery?
    ): Observable<STKPushResponse?>?
}