# mpesa-android 

Mpesa-Android makes it easy to process online checkout using mpesa as the preferred method of payment.

## Getting Started

To get started follow this link to create an account with safaricom developer
[here](https://developer.safaricom.co.ke/).

To make an API call, you will need:
* A Consumer key
* A Consumer Secret
 
Remember to replace the test key with your live key in production.

For testing purposes, you can get test credentials here. Use the Lipa Na Mpesa Online Shortcode 
and Lipa Na Mpesa Online Passkey from the link.
[Lipa na Mpesa Test credentials](https://developer.safaricom.co.ke/test_credentials)

### Installing

Basic Usage:

Step 1. **Add the JitPack repository to your build file**

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. ** Add the dependency **

```
dependencies {
	        compile 'com.github.freddygenicho:mpesa-android:1.1.0'
	}
```

Step 3. ** Initialize the SDK **

Create an instance of Mpesa class: 

Note: 

*For Development Purpose* use ```Mode.SANDOX``` 

*For Production Purpose*  use ```Mode.PRODUCTION``` 

```
Mpesa mpesa = new Mpesa(YOUR_CONSUMER_KEY, YOUR_CONSUMER_SECRET, Mode.SANDBOX);
```

Step 4. Call `getToken()` method to get an authorization token from mpesa API

```
mpesa.getToken(new TokenListener() {
                @Override
                public void onToken(Token token) {
                    //todo save token or call the stkpush();
                }

                @Override
                public void OnError(Throwable throwable) {
                    //called when an error occures
                }
            });
```

* ```public void onToken(Token token)```

This method is called when initializing the Mpesa instance is successful.
Therefore, you can use this method to make a transaction.

* ```public void OnError(Throwable throwable))```

    In case of any error an exception is thrown. You can `Log.error(throwable.getMessage)`
     to view the error. Make sure your credentials are correct.


## Making a transaction
REQUIREMENTS:
1. A business shortcode. For testing purposes.
2. A pass key. 
3. The amount to transact.
4. The phone number making the payment.

You can get test credential [here](https://developer.safaricom.co.ke/test_credentials)

To make a transaction , create an instance of `STKPush` and pass in `Token`, `STKPush`, and 
a callback listener `new STKListener()`.

```
    STKPush stkPush = new STKPush();
    stkPush.setBusinessShortCode(BUSINESS_SHORT_CODE);
    stkPush.setTimestamp(STKPush.getTimestamp());
    stkPush.setTransactionType(TRANSACTION_TYPE);
    stkPush.setAmount(ENTER_AMOUNT);
    stkPush.setPartyA(STKPush.sanitizePhoneNumber(ENTER_PHONE_NUMBER));
    stkPush.setPartyB(ENTER_PARTYB);
    stkPush.setPhoneNumber(STKPush.sanitizePhoneNumber(ENTER_PHONE_NUMBER));
    stkPush.setCallBackURL(ENTER_CALLBACKURL);
    stkPush.setAccountReference("test");
    stkPush.setTransactionDesc("some description");
```
 Call this method to pop up the STK push
 
```
      mpesa.startStkPush(token, stkPush, new STKListener() {
            @Override
            public void onResponse(STKPushResponse stkPushResponse) {
                Log.e(TAG, "onResponse: " + stkPushResponse.toJson(stkPushResponse));
                }
 
            @Override
            public void onError(Throwable throwable) {
                 Log.e(TAG, "onError: " + throwable.getMessage());
            }
      });
```


## Built With

* [GSON](https://github.com/google/gson/) - A Java library that can be used to convert Java Objects into their JSON representation
* [RETROFIT](http://square.github.io/retrofit/) - Retrofit turns your HTTP API into a Java interface.
* [OKHTTP](http://square.github.io/okhttp/) - HTTP is the way modern applications network. It’s how we exchange data & media
* [RXJAVA](https://github.com/ReactiveX/RxJava/) - A library for composing asynchronous and event-based programs by using observable sequences.


## Authors

* **Fredrick Ochieng**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
