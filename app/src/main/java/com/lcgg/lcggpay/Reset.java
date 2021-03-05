package com.lcgg.lcggpay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Reset extends AppCompatActivity {

    EditText txt_email;

    Button btnSubmit;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        txt_email = findViewById(R.id.txt_reset_email);

        btnSubmit = findViewById(R.id.btn_reset_submit);
        btnCancel = findViewById(R.id.btn_reset_cancel);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.setVisibility(View.GONE);
                btnCancel.setText("Done");

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}