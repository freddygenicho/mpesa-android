package com.freddygenicho.mpesa.stkpush.interfaces;

import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse;

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */

public interface STKListener {

    void onResponse(STKPushResponse stkPushResponse);

    void onError(Throwable throwable);
}
