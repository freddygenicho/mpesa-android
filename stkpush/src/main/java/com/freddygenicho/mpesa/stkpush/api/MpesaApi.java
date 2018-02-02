package com.freddygenicho.mpesa.stkpush.api;

import com.freddygenicho.mpesa.stkpush.model.Token;
import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse;
import com.freddygenicho.mpesa.stkpush.model.STKPush;
import com.freddygenicho.mpesa.stkpush.model.STKQuery;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */

public interface MpesaApi {

    @Headers({"Cache-Control: max-age=640000", "cache-control: no-cache"})
    @GET("oauth/v1/generate?grant_type=client_credentials")
    Observable<Token> generateAccessToken(@Header("Authorization") String authorization);

    @Headers("content-type: application/json")
    @POST("mpesa/stkpush/v1/processrequest")
    Observable<STKPushResponse> stkPush(@Header("Authorization") String authorization, @Body STKPush stkPush);

    @Headers("content-type: application/json")
    @POST("mpesa/stkpush/v1/query")
    Observable<STKPushResponse> stkPushQuery(@Header("Authorization") String authorization, @Body STKQuery stkQuery);

}
