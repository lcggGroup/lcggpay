package com.lcgg.lcggpay;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amplifyframework.auth.AuthProvider;
import com.amplifyframework.core.Amplify;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_pay, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        AppCenter.start(getApplication(), "a0dfaf0e-a308-4fde-bb0d-c83219f54eb3",
                Analytics.class, Crashes.class);

        //login();

    }

    private void login() {
        Amplify.Auth.signInWithSocialWebUI(
                AuthProvider.amazon(),
                this,
                result -> {
                    Log.i(TAG, "AuthQuickstart RESULT " + result.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                },
                error -> {
                    Log.e(TAG, "AuthQuickstart ERROR " + error.toString());
                }
        );

    }

}