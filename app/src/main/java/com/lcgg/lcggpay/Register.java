package com.lcgg.lcggpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText txt_reg_username;
    EditText txt_reg_password;
    EditText txt_reg_retype_pass;

    Button btnSubmit;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        txt_reg_username = findViewById(R.id.txt_reg_username);
        txt_reg_password = findViewById(R.id.txt_reg_username);
        txt_reg_retype_pass = findViewById(R.id.txt_reg_retype_pass);

        btnSubmit = findViewById(R.id.btn_reg_submit);
        btnCancel = findViewById(R.id.btn_reg_cancel);

        btnSubmit.setClickable(false);

        if (TextUtils.isEmpty(txt_reg_username.getText().toString())) {
            txt_reg_retype_pass.setError("Enter Email");
        }
        if(!(txt_reg_password.getText().toString().equals(txt_reg_retype_pass.getText().toString()))){
            txt_reg_retype_pass.setError("Password Not matching");
        }
        else if (!(TextUtils.isEmpty(txt_reg_username.getText().toString())) &&
                (txt_reg_password.getText().toString().equals(txt_reg_retype_pass.getText().toString()))){
            btnSubmit.setClickable(true);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(txt_reg_username.getText().toString())) {
                    txt_reg_username.setError("Enter an email address for your username");
                }
                else if (TextUtils.isEmpty(txt_reg_password.getText().toString())) {
                    txt_reg_password.setError("Enter your password");
                }
                else if (TextUtils.isEmpty(txt_reg_retype_pass.getText().toString())) {
                    txt_reg_retype_pass.setError("Confirm your password");
                }
                else if (!txt_reg_password.getText().toString().equals(txt_reg_retype_pass.getText().toString())) {
                    txt_reg_password.setError("Your password must be must with the confirmed password");
                }
                else {
                    mAuth.createUserWithEmailAndPassword(txt_reg_username.getText().toString(), txt_reg_password.getText().toString())
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        startActivity(new Intent(Register.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}