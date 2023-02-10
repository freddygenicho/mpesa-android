package com.freddygenicho.mpesa.stkpush.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

/**
 * @author Fredrick Ochieng on 02/02/2018.
 */
class Token {
    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null

    @SerializedName("expires_in")
    @Expose
    var expiresIn: String? = null
}