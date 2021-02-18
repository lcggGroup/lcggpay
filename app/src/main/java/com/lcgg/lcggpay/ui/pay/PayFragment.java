package com.lcgg.lcggpay.ui.pay;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lcgg.lcggpay.R;
import com.lcgg.lcggpay.ui.dashboard.DashboardViewModel;

public class PayFragment extends Fragment {

    private PayViewModel payViewModel;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this.getActivity());
        intentIntegrator.initiateScan();

        payViewModel =
                new ViewModelProvider(this).get(PayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pay, container, false);
        textView = root.findViewById(R.id.text_pay);
        payViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult =IntentIntegrator.parseActivityResult(requestCode,resultCode, data);

        if (intentResult != null){
            if (intentResult.getContents() != null){
                textView.setText("Cancelled");
            }
            else {
                textView.setText(intentResult.getContents());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}