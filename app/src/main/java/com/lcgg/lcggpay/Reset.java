package com.lcgg.lcggpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseAuth;

public class Reset extends AppCompatActivity {

    private FirebaseAuth mAuth;

    TextView txt_message;
    TextView txt_notice;
    EditText txt_email;

    Button btnSubmit;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        mAuth = FirebaseAuth.getInstance();

        txt_message = findViewById(R.id.txt_reset_message);
        txt_notice = findViewById(R.id.txt_reset_notice);
        txt_email = findViewById(R.id.txt_reset_email);

        btnSubmit = findViewById(R.id.btn_reset_submit);
        btnCancel = findViewById(R.id.btn_reset_cancel);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(txt_email.getText().toString()) ||
                    !isEmailValid(txt_email.getText().toString())) {
                    txt_email.setError("Enter your registered email id");
                    txt_email.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (!TextUtils.isEmpty(txt_email.getText().toString())) {
                    txt_email.setBackgroundResource(R.drawable.txt_design_box);

                    mAuth.sendPasswordResetEmail(txt_email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        txt_message.setText("We have sent you instructions to reset your password!");
                                        txt_notice.setVisibility(View.GONE);
                                        txt_email.setVisibility(View.GONE);
                                        btnSubmit.setVisibility(View.GONE);
                                        btnCancel.setText("Done");
                                    }
                                    else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Reset.this);
                                        builder.setTitle("Reset");
                                        builder.setMessage(task.getException().getMessage());
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // You don't have to do anything here if you just
                                                // want it dismissed when clicked
                                            }
                                        });
                                        builder.create().show();
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

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}