package com.lcgg.lcggpay.confirmation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lcgg.lcggpay.R;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCenter.start(getApplication(), "a0dfaf0e-a308-4fde-bb0d-c83219f54eb3",
                Analytics.class, Crashes.class);
    }
}