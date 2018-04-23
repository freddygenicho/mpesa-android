package com.freddygenicho.mpesa.stkpush.interfaces;

import com.freddygenicho.mpesa.stkpush.model.Token;

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */

public interface TokenListener {

    /**
     * method callback when token is generated successfully
     *
     * @param token - object from mpesa api response
     */
    void onTokenSuccess(Token token);

    /**
     * called when an error occurs
     *
     * @param throwable - an exception
     */
    void OnTokenError(Throwable throwable);
}
