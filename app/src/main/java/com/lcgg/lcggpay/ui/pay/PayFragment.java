package com.lcgg.lcggpay.ui.pay;

import android.Manifest;
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
import com.lcgg.lcggpay.R;
import com.lcgg.lcggpay.confirmation.AcceptedActivity;
import com.lcgg.lcggpay.confirmation.PaymentActivity;

public class PayFragment extends Fragment {
    private TextView textView;
    public String amt = "0";

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
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else {
                //Successful Scan
                //Intent intent = new Intent(getActivity(), PaymentActivity.class);
                //intent.putExtra("amount","15");
                //intent.putExtra("URL", result.getContents());
                //startActivityForResult(intent, );
                Toast.makeText(getContext(), requestCode, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}