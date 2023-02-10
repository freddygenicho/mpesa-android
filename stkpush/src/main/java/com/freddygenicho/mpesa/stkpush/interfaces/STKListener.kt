package com.freddygenicho.mpesa.stkpush.interfaces

import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */
interface STKListener {
    fun onResponse(stkPushResponse: STKPushResponse?)
    fun onError(throwable: Throwable?)
}