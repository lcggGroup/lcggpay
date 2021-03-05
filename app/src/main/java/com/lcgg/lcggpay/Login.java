package com.lcgg.lcggpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lcgg.lcggpay.data.model.PayPal;
import com.paypal.android.sdk.payments.PayPalService;

public class Login extends AppCompatActivity {

    private static final String TAG = "FragmentActivity";
    private FirebaseAuth mAuth;

    EditText txt_username;
    EditText txt_password;

    TextView txt_register;
    TextView txt_reset;

    Button btnSubmit;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        txt_register = findViewById(R.id.txt_register);
        txt_reset = findViewById(R.id.txt_reset);


        btnSubmit = findViewById(R.id.btn_submit);
        btnCancel = findViewById(R.id.btn_cancel);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txt_username.getText().toString().isEmpty() || txt_username.getText().toString() == null) {
                    txt_username.setError("Enter the email address you've registered as your username.");
                }
                if (txt_password.getText().toString().isEmpty() || txt_password.getText().toString() == null) {
                    txt_password.setError("Enter your password");
                }
                else if (!(txt_username.getText().toString().isEmpty() || txt_username.getText().toString() == null) &&
                        !(txt_password.getText().toString().isEmpty() || txt_password.getText().toString() == null)){

                    mAuth.signInWithEmailAndPassword(txt_username.getText().toString(), txt_password.getText().toString())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (!isEmailValid(txt_username.getText().toString())) {
                                            txt_username.setError("Your Email Id is Invalid.");
                                        }
                                        if (txt_password.getText().toString().length() < 6 ) {
                                            txt_password.setError("Minimum length is 7");
                                        }
                                        else {
                                            Toast.makeText(Login.this, "Login failed" , Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        startActivity(intent);
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
                System.exit(0);
            }
        });

        txt_register.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "register", Toast.LENGTH_SHORT).show();
            }
        });

        txt_reset.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this, "reset", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}