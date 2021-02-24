package com.lcgg.lcggpay.ui.pay;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lcgg.lcggpay.PayPal;
import com.lcgg.lcggpay.R;
import com.lcgg.lcggpay.confirmation.AcceptedActivity;
import com.lcgg.lcggpay.confirmation.CancelledActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import static android.app.Activity.RESULT_OK;

public class PayFragment extends Fragment {
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(PayFragment.this);
        intentIntegrator.setPrompt("");
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.initiateScan();

        View root = inflater.inflate(R.layout.fragment_pay, container, false);
        textView = root.findViewById(R.id.text_pay);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if (result.getContents() == null) {
                //Cancel Scan
            }
            else {
                String amount = "15";
                PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD",
                        "Purchase Goods",PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(getContext(), AcceptedActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPal.PAYPAL_CONFIG);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
                startActivityForResult(intent, PayPal.PAYPAL_REQUEST_CODE);
            }
        }
    }
}