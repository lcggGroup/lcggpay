package com.lcgg.lcggpay.confirmation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lcgg.lcggpay.R;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import org.json.JSONException;
import org.json.JSONObject;

public class AcceptedActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCenter.start(getApplication(), "a0dfaf0e-a308-4fde-bb0d-c83219f54eb3",
                Analytics.class, Crashes.class);

        Intent intent = getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            Toast.makeText(this, response.getString("id"), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, response.getString("state"), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "$" + paymentAmount, Toast.LENGTH_SHORT).show();

            //txtId.setText(response.getString("id"));
            //txtStatus.setText(response.getString("state"));
            //txtAmount.setText("$"+paymentAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}