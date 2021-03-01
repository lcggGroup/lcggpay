package com.lcgg.lcggpay.ui.pay;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lcgg.lcggpay.R;
import com.lcgg.lcggpay.confirmation.PaymentActivity;
import com.lcgg.lcggpay.data.model.PayPal;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;

import java.math.BigDecimal;

public class PayFragment extends Fragment {
    Button btnConfirm;
    Button btnCancel;

    TextView txt_title;
    TextView txt_amount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(PayFragment.this);
        intentIntegrator.setPrompt("");
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();

        View root = inflater.inflate(R.layout.fragment_pay, container, false);

        btnConfirm = root.findViewById(R.id.btn_confirm);
        btnCancel = root.findViewById(R.id.btn_cancel);

        txt_title = root.findViewById(R.id.txt_title);
        txt_amount = root.findViewById(R.id.txt_amount);

        //start paypal service
        Intent intent = new Intent(getActivity(),PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPal.PAYPAL_CONFIG);
        getActivity().startService(intent);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Confirm", Toast.LENGTH_SHORT).show();
                processPayment();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }

    private void processPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(txt_amount.getText().toString()),"USD",
                txt_title.getText().toString(), PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPal.PAYPAL_CONFIG);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent, PayPal.PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if (result.getContents() == null) {
                //Cancel Scan
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else {
                //Successful Scan
                txt_title.setText("Sample Title");
                txt_amount.setText("15");
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}