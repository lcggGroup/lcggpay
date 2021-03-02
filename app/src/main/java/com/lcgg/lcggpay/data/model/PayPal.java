package com.lcgg.lcggpay.data.model;

import com.paypal.android.sdk.payments.PayPalConfiguration;

public class PayPal {
    public static final int PAYPAL_REQUEST_CODE = 49374;

    public static final String PAYPAL_CLIENT_ID_SANDBOX = "AU8OapxNuCvfSOUzEVlbwrzhs4i547trZ1qqhSzhOYr4ykcZHKOslqILpCXO6YjMUESoRTB5Lmx5KA1I";
    public static final String PAYPAL_CLIENT_ID_LIVE = "AXJZVreUJNT33unQP6PWnPwDSuJN-5GZWVwoDrvemvPbJNK6uNfNTOQ5BmWckuZSt6KU5k30ZgvBmhci";

    public static final PayPalConfiguration PAYPAL_CONFIG = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID_SANDBOX);
}
