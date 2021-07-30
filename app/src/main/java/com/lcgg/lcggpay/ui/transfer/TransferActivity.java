package com.lcgg.lcggpay.ui.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lcgg.lcggpay.MainActivity;
import com.lcgg.lcggpay.R;

public class TransferActivity extends AppCompatActivity {

    Intent intent;

    Button cancel;
    Button submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        cancel = findViewById(R.id.transferBtn_cancel);
        submit = findViewById(R.id.transferBtn_submit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}