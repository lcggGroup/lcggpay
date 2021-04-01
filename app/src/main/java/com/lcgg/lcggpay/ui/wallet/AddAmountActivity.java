package com.lcgg.lcggpay.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.lcgg.lcggpay.MainActivity;
import com.lcgg.lcggpay.R;
import com.lcgg.lcggpay.ui.pay.VerificationPage;

public class AddAmountActivity extends AppCompatActivity {

    Intent intent;
    String valueActivity;
    private CompoundBarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pay);

        //ToDo: get values
        Intent getIntent = getIntent();
        Bundle b = getIntent.getExtras();

        // Values = Add, Transfer, Withdraw
        valueActivity = b.getString("valueActivity");

        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt(null);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    public void onResume() {
        barcodeView.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        barcodeView.pause();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                //ToDo: Create new page for verified transaction
                intent = new Intent(this, WalletVerificationPage.class);
                intent.putExtra("UserId", result.getContents());
                intent.putExtra("valueActivity", valueActivity);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}