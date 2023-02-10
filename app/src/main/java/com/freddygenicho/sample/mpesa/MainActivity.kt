package com.freddygenicho.sample.mpesa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.freddygenicho.mpesa.stkpush.Mode
import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse
import com.freddygenicho.mpesa.stkpush.interfaces.STKListener
import com.freddygenicho.mpesa.stkpush.interfaces.TokenListener
import com.freddygenicho.mpesa.stkpush.model.Mpesa
import com.freddygenicho.mpesa.stkpush.model.STKPush
import com.freddygenicho.mpesa.stkpush.model.Token
import com.freddygenicho.mpesa.stkpush.model.Transaction
import java.io.UnsupportedEncodingException

class MainActivity : AppCompatActivity(), TokenListener {
    private var phoneET: EditText? = null
    private var amountET: EditText? = null
    private var sweetAlertDialog: SweetAlertDialog? = null
    private var mpesa: Mpesa? = null
    private var phoneNumber: String? = null
    private var amount: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        phoneET = findViewById(R.id.phoneET)
        amountET = findViewById(R.id.amountET)
        mpesa = Mpesa(Config.CONSUMER_KEY, Config.CONSUMER_SECRET, Mode.SANDBOX)

    }

    fun startMpesa(view: View?) {
        phoneNumber = phoneET!!.text.toString()
        amount = amountET!!.text.toString()
        if (phoneNumber!!.isEmpty()) {
            Toast.makeText(this@MainActivity, "Phone Number is required", Toast.LENGTH_SHORT).show()
            return
        }
        if (amount!!.isEmpty()) {
            Toast.makeText(this@MainActivity, "Amount is required", Toast.LENGTH_SHORT).show()
            return
        }
        if (phoneNumber!!.isNotEmpty() && amount!!.isNotEmpty()) {
            try {
                mpesa!!.getToken(this)
            } catch (e: UnsupportedEncodingException) {
                Log.e(TAG, "UnsupportedEncodingException: " + e.localizedMessage)
            }
        } else {
            Toast.makeText(
                this@MainActivity,
                "Please make sure that phone number and amount is not empty ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onTokenSuccess(token: Token?) {
        sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog!!.titleText = "Connecting to Mpesa"
        sweetAlertDialog!!.contentText = "Please wait..."
        sweetAlertDialog!!.setCancelable(false)
        sweetAlertDialog!!.show()

        val stkPush = STKPush()
        stkPush.businessShortCode = Config.BUSINESS_SHORT_CODE
        stkPush.password =
            STKPush.getPassword(Config.BUSINESS_SHORT_CODE, Config.PASSKEY, STKPush.getTimestamp())
        stkPush.setTimestamp(STKPush.getTimestamp())
        stkPush.transactionType = Transaction.CUSTOMER_PAY_BILL_ONLINE
        stkPush.amount = amount
        stkPush.partyA = STKPush.sanitizePhoneNumber(phoneNumber!!)
        stkPush.partyB = Config.PARTYB
        stkPush.phoneNumber = STKPush.sanitizePhoneNumber(phoneNumber!!)
        stkPush.callBackURL = Config.CALLBACKURL
        stkPush.accountReference = "test"
        stkPush.transactionDesc = "some description"
        mpesa!!.startStkPush(token, stkPush, object : STKListener {
            override fun onResponse(stkPushResponse: STKPushResponse?) {
                Log.e(TAG, "onResponse: " + stkPushResponse?.toJson(stkPushResponse))
                val message = "Please enter your pin to complete transaction"
                sweetAlertDialog!!.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                sweetAlertDialog!!.titleText = "Transaction started"
                sweetAlertDialog!!.contentText = message
            }

            override fun onError(throwable: Throwable?) {
                Log.e(TAG, "stk onError: " + throwable?.message)
                sweetAlertDialog!!.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                sweetAlertDialog!!.titleText = "Error"
                sweetAlertDialog!!.contentText = throwable?.message
            }
        })
    }

    override fun onTokenError(throwable: Throwable?) {
        Log.e(TAG, "mpesa Error: " + throwable!!.message)
        sweetAlertDialog!!.changeAlertType(SweetAlertDialog.ERROR_TYPE)
        sweetAlertDialog!!.titleText = "Error"
        sweetAlertDialog!!.contentText = throwable.message
    }

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}