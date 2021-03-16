package com.lcgg.lcggpay.ui.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lcgg.lcggpay.R;
import com.lcgg.lcggpay.Wallet;

public class WalletFragment extends Fragment {

    EditText txtBalance;
    Button addFunds;
    Button transferFunds;
    Button withdrawFunds;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_wallet, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtBalance = root.findViewById(R.id.wallet_balance);

        addFunds = root.findViewById(R.id.btn_add);
        transferFunds = root.findViewById(R.id.btn_transfer);
        withdrawFunds = root.findViewById(R.id.btn_withdraw);

        //Get Amount - User
        mDatabase.child(mAuth.getUid()).child("Wallet").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Wallet wallet = postSnapshot.getValue(Wallet.class);
                    txtBalance.setText(wallet.getAmount().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Add", Toast.LENGTH_SHORT).show();
            }
        });

        transferFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Transfer", Toast.LENGTH_SHORT).show();
            }
        });

        withdrawFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Withdraw", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}