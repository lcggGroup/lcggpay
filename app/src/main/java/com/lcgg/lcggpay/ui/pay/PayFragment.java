package com.lcgg.lcggpay.ui.pay;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.lcgg.lcggpay.MainActivity;
import com.lcgg.lcggpay.R;

import java.util.List;

public class PayFragment extends Fragment {

    Intent intent;
    private BarcodeView barcodeView;
    //private IntentIntegrator integrator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View root = inflater.inflate(R.layout.fragment_pay, container, false);
        barcodeView = (BarcodeView) root.findViewById(R.id.barcode_scanner);

        //integrator = IntentIntegrator.forSupportFragment(PayFragment.this);
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        //integrator.initiateScan();

        //barcodeView.initializeFromIntent(integrator.createScanIntent());
        barcodeView.decodeContinuous(callback);

        return root;
    }

    //@Override
    //public void onClick(View view)
    //{
    //
    //}

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            //if (result.getText() != null) {
            //    barcodeView.setStatusText(result.getText());
            //}

            //Do something with code result
            Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    @Override
    public void onResume() {
        //barcodeView.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        barcodeView.pause();
        super.onPause();
    }

}