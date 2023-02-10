package com.freddygenicho.mpesa.stkpush.model

import android.util.Base64
import com.freddygenicho.mpesa.stkpush.Mode
import com.freddygenicho.mpesa.stkpush.api.RetroClient
import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse
import com.freddygenicho.mpesa.stkpush.interfaces.STKListener
import com.freddygenicho.mpesa.stkpush.interfaces.STKQueryListener
import com.freddygenicho.mpesa.stkpush.interfaces.TokenListener
import com.freddygenicho.mpesa.stkpush.model.Mpesa
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.io.UnsupportedEncodingException

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */
class Mpesa(
    private val consumerKey: String,
    private val consumerSecret: String,
    private val mode: Mode
) {
    private val mCompositeSubscription: CompositeSubscription = CompositeSubscription()

    /**
     * returns mpesa access token
     *
     * @param tokenListener - callback listener
     */
    @Throws(UnsupportedEncodingException::class)
    fun getToken(tokenListener: TokenListener?) {
        if (tokenListener == null) {
            throw RuntimeException("Activity must implement TokenListener")
        }
        mCompositeSubscription.add(
            RetroClient.getApiService(mode).generateAccessToken(auth)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(object : Subscriber<Token?>() {
                    override fun onCompleted() {}
                    override fun onError(e: Throwable) {
                        tokenListener.onTokenError(e)
                    }

                    override fun onNext(token: Token?) {
                        tokenListener.onTokenSuccess(token)
                    }
                })
        )
    }

    /**
     * generate mpesa bearer key
     *
     * @return - encoded auth string
     * @throws UnsupportedEncodingException - exception
     */
    @get:Throws(UnsupportedEncodingException::class)
    private val auth: String
        get() {
            if (consumerKey.isEmpty()) {
                throw RuntimeException("Consumer key cannot be empty")
            }
            if (consumerSecret.isEmpty()) {
                throw RuntimeException("Consumer secret cannot be empty")
            }
            val consumerKeySecret = "$consumerKey:$consumerSecret"
            val bytes = consumerKeySecret.toByteArray(charset("ISO-8859-1"))
            return "Basic " + Base64.encodeToString(bytes, Base64.NO_WRAP)
        }

    /**
     * start mpesa push stk
     *
     * @param token       - access token from mpesa
     * @param stkPush     - stk push object
     * @param stkListener - callback method
     */
    fun startStkPush(token: Token?, stkPush: STKPush?, stkListener: STKListener?) {
        if (token == null) {
            throw RuntimeException("Token cannot be null")
        }
        if (stkPush == null) {
            throw RuntimeException("STKPush cannot be null")
        }
        if (stkListener == null) {
            throw RuntimeException("Activity must implement TokenListener")
        }
        if (stkPush.callBackURL == null) {
            throw RuntimeException("Callback URL cannot be null")
        }
        if (stkPush.callBackURL!!.isEmpty()) {
            throw RuntimeException("Callback URL is required")
        }
        if (stkPush.password == null) {
            throw RuntimeException("Password can not be null")
        }
        if (stkPush.password!!.isEmpty()) {
            throw RuntimeException("Password is required")
        }
        mCompositeSubscription.add(
            RetroClient.getApiService(mode)
                .stkPush(token.accessToken?.let { getAuthorization(it) }, stkPush)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(object : Subscriber<STKPushResponse?>() {
                    override fun onCompleted() {}
                    override fun onError(throwable: Throwable) {
                        stkListener.onError(throwable)
                    }

                    override fun onNext(stkPushResponse: STKPushResponse?) {
                        stkListener.onResponse(stkPushResponse)
                    }
                })
        )
    }

    private fun getAuthorization(accessToken: String): String {
        return "Bearer $accessToken"
    }

    fun stkPushQuery(token: Token?, stkQuery: STKQuery?, stkQueryListener: STKQueryListener?) {
        if (token == null) {
            throw RuntimeException("Token cannot be null")
        }
        if (stkQuery == null) {
            throw RuntimeException("STKQuery cannot be null")
        }
        if (stkQueryListener == null) {
            throw RuntimeException("Activity must implement STKQueryListener")
        }
        mCompositeSubscription.add(
            RetroClient.getApiService(mode)
                .stkPushQuery(token.accessToken?.let { getAuthorization(it) }, stkQuery)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(object : Subscriber<STKPushResponse?>() {
                    override fun onCompleted() {}
                    override fun onError(e: Throwable) {
                        stkQueryListener.onError(e)
                    }

                    override fun onNext(stkPushResponse: STKPushResponse?) {
                        stkQueryListener.onResponse(stkPushResponse)
                    }
                })
        )
    }

    companion object {
        private val TAG = Mpesa::class.java.simpleName
    }
}