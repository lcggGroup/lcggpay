package com.lcgg.lcggpay.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.lcgg.lcggpay.Login;
import com.lcgg.lcggpay.R;

public class LoginCredentials extends AppCompatActivity {
    Intent intent;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_credential);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}