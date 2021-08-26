package com.lcgg.lcggpay.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.lcgg.lcggpay.Login;
import com.lcgg.lcggpay.R;

public class LoginCredentials extends AppCompatActivity {
    Button edit;
    Button save;

    EditText passEdit;
    EditText rePassEdit;

    TextView pass;
    TextView rePass;

    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_credential);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        save = findViewById(R.id.loginBtn_save);
        edit = findViewById(R.id.loginBtn_pass);

        pass = findViewById(R.id.txt_login_cred_pass);
        rePass = findViewById(R.id.txt_login_cred_re_pass);
        passEdit = findViewById(R.id.txt_login_cred_passEdit);
        rePassEdit = findViewById(R.id.txt_login_cred_re_passEdit);

        save.setVisibility(View.GONE);

        pass.setVisibility(View.GONE);
        rePass.setVisibility(View.GONE);
        rePassEdit.setVisibility(View.GONE);
        passEdit.setVisibility(View.GONE);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pass.setVisibility(View.VISIBLE);
                rePass.setVisibility(View.VISIBLE);
                rePassEdit.setVisibility(View.VISIBLE);
                passEdit.setVisibility(View.VISIBLE);

                //edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);

                edit.setText("Cancel");
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(LoginCredentials.this, LoginCredentials.class);
                        startActivity(intent);
                    }
                });


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);

                pass.setVisibility(View.GONE);
                rePass.setVisibility(View.GONE);
                rePassEdit.setVisibility(View.GONE);
                passEdit.setVisibility(View.GONE);
            }
        });
    }
}