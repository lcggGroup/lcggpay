package com.lcgg.lcggpay.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lcgg.lcggpay.R;
import com.lcgg.lcggpay.Wallet;
import com.lcgg.lcggpay.ui.pay.PayFragment;

public class WalletFragment extends Fragment {

    EditText txtBalance;
    Button addFunds;
    Button transferFunds;
    Button withdrawFunds;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private IntentIntegrator integrator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference(mAuth.getUid()).child("Wallet");

        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        txtBalance = root.findViewById(R.id.wallet_balance);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Wallet wallet = snapshot.getValue(Wallet.class);

                txtBalance.setText(wallet.getAmount().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addFunds = root.findViewById(R.id.btn_add);
        transferFunds = root.findViewById(R.id.btn_transfer);
        withdrawFunds = root.findViewById(R.id.btn_withdraw);


        addFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        transferFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integrator = IntentIntegrator.forSupportFragment(WalletFragment.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.initiateScan();
            }
        });

        withdrawFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}