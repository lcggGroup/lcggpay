package com.lcgg.lcggpay.confirmation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lcgg.lcggpay.data.model.PayPal;
import com.lcgg.lcggpay.R;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import static com.paypal.android.sdk.payments.PayPalProfileSharingActivity.RESULT_EXTRAS_INVALID;
import static com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION;

public class PaymentActivity extends AppCompatActivity {

    Intent intentPay;
    String amount = intentPay.getStringExtra("amount");

    Button btnConfirm;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_paypal);

        AppCenter.start(getApplication(), "a0dfaf0e-a308-4fde-bb0d-c83219f54eb3",
                Analytics.class, Crashes.class);

        //Start PayPal Service
        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,PayPal.PAYPAL_CONFIG);
        startService(intent);

        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancel);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //processPayment();
                Toast.makeText(PaymentActivity.this, "Confirmed", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void processPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD",
                "Purchase Goods",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, AcceptedActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPal.PAYPAL_CONFIG);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent, PayPal.PAYPAL_REQUEST_CODE);
    }


}