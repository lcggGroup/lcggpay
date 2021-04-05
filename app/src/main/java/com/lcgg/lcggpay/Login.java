package com.lcgg.lcggpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
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

                if (TextUtils.isEmpty(txt_username.getText().toString()) ||
                        !isEmailValid(txt_username.getText().toString())) {
                    txt_username.setError("Enter valid email address for your username");
                    txt_username.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (!TextUtils.isEmpty(txt_username.getText().toString()) ||
                        isEmailValid(txt_username.getText().toString())) {
                    txt_username.setBackgroundResource(R.drawable.txt_design_box);
                }

                if (TextUtils.isEmpty(txt_password.getText().toString())) {
                    txt_password.setBackgroundResource(R.drawable.txt_design_box_red);
                    txt_password.setError("Enter your password");
                }
                else if (!TextUtils.isEmpty(txt_password.getText().toString())) {
                    txt_password.setBackgroundResource(R.drawable.txt_design_box);
                }

                if (!(TextUtils.isEmpty(txt_username.getText().toString())) && !(TextUtils.isEmpty(txt_password.getText().toString()))){
                    mAuth.signInWithEmailAndPassword(txt_username.getText().toString(), txt_password.getText().toString())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                        builder.setTitle("Login");
                                        builder.setMessage(task.getException().getMessage());
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // You don't have to do anything here if you just
                                                // want it dismissed when clicked
                                            }
                                        });
                                        builder.create().show();

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
                //System.exit(0);
            }
        });

        txt_register.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        txt_reset.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Reset.class);
                startActivity(intent);
            }
        });
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}