package com.lcgg.lcggpay.ui.pay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.lcgg.lcggpay.MainActivity;
import com.lcgg.lcggpay.R;
import com.lcgg.lcggpay.Register;
import com.lcgg.lcggpay.Reset;
import com.lcgg.lcggpay.Wallet;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class VerificationPage extends AppCompatActivity {

    private static final int MAX_LENGTH = 15;
    private static char tempChar;
    LinearLayout verifyLayout;
    LinearLayout confirmLayout;
    LinearLayout dateLayout;
    //private DatabaseReference myRef = database.getReference("pay");
    LinearLayout walletLayout;
    EditText txtAmount;
    TextView txtPayableTo;
    TextView txtPay;
    TextView txtConfirm;
    TextView txtTransDate;
    TextView txtWalletBal;
    Button btnSubmit;
    Button btnCancel;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);

        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_page);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        verifyLayout = findViewById(R.id.verifyLayout);
        confirmLayout = findViewById(R.id.ll_confirm);
        dateLayout = findViewById(R.id.ll_date);
        walletLayout = findViewById(R.id.ll_wallet);

        txtAmount = findViewById(R.id.txt_amount);
        txtPay = findViewById(R.id.txt_pay);
        txtPayableTo = findViewById(R.id.txt_userId);
        txtConfirm = findViewById(R.id.txt_confirmId);
        txtTransDate = findViewById(R.id.txt_transDate);
        txtWalletBal = findViewById(R.id.txt_wallet_balance);

        btnSubmit = findViewById(R.id.btn_verify_submit);
        btnCancel = findViewById(R.id.btn_verify_cancel);

        //Confirmation Number
        confirmLayout.setVisibility(View.GONE);
        //Transaction Date
        dateLayout.setVisibility(View.GONE);
        //Wallet Balance
        walletLayout.setVisibility(View.VISIBLE);

        btnSubmit.setEnabled(false);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        txtPayableTo.setText(b.getString("UserId"));

        //Get Amount - User
        mDatabase.child(mAuth.getUid()).child("Wallet").getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Wallet wallet = postSnapshot.getValue(Wallet.class);
                    txtWalletBal.setText(wallet.getAmount().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (TextUtils.isEmpty(txtAmount.getText().toString())) {
            btnSubmit.setEnabled(false);
            txtAmount.setError("Amount must not be empty");
        }
        else if (!TextUtils.isEmpty(txtAmount.getText().toString())) {
            btnSubmit.setEnabled(true);

        }

        //To be verified
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPay.setText("Verify your Payment");
                txtAmount.setEnabled(false);

                if (Double.parseDouble(txtAmount.getText().toString()) > Double.parseDouble(txtWalletBal.getText().toString())){
                    btnSubmit.setEnabled(false);

                    AlertDialog.Builder builder = new AlertDialog.Builder(VerificationPage.this, R.style.question_dialog);
                    builder.setTitle("Wallet");
                    builder.setMessage("Insufficient Funds. Please load your wallet.");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // You don't have to do anything here if you just
                            // want it dismissed when clicked
                        }
                    });
                    builder.create().show();
                }
                else {
                    btnSubmit.setEnabled(true);
                }

                //Verified
                btnSubmit.setText("Confirm");
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date date = new Date();
                        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                        String strDate = dateFormat.format(date);

                        txtPay.setText("Confirmed Payment");
                        txtAmount.setEnabled(false);

                        //Confirmation #
                        confirmLayout.setVisibility(View.VISIBLE);
                        //Generate Unique ID for Confirmation #
                        String confirmId = random();
                        txtConfirm.setText(confirmId);
                        //Transaction Date
                        dateLayout.setVisibility(View.VISIBLE);
                        txtTransDate.setText(strDate);
                        //Wallet Balance
                        walletLayout.setVisibility(View.GONE);

                        btnSubmit.setVisibility(View.GONE);
                        btnCancel.setText("Done");

                        //Get Amount - User
                        mDatabase.child(mAuth.getUid()).child("Wallet").getRef().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    Wallet wallet = postSnapshot.getValue(Wallet.class);
                                    Double newAmount = wallet.getAmount() - Double.parseDouble(txtAmount.getText().toString());

                                    //Update Amount Value
                                    mDatabase.child(mAuth.getUid()).child("Wallet").child("amount").setValue(newAmount);

                                    //Save transactions
                                    mDatabase.child(mAuth.getUid()).child("Wallet").child("Transactions").child("Pay").child(txtPayableTo.getText().toString()).child(txtConfirm.getText().toString()).child("transactionDate").setValue(strDate);
                                    mDatabase.child(mAuth.getUid()).child("Wallet").child("Transactions").child("Pay").child(txtPayableTo.getText().toString()).child(txtConfirm.getText().toString()).child("amount").setValue(txtAmount.getText().toString());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        //Get Amount - Seller
                        mDatabase.child(txtPayableTo.getText().toString()).child("Wallet").getRef().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    Wallet wallet = postSnapshot.getValue(Wallet.class);
                                    Double newAmount = wallet.amount + Double.parseDouble(txtAmount.getText().toString());

                                    //Update Amount Value
                                    mDatabase.child(txtPayableTo.getText().toString()).child("Wallet").child("amount").setValue(newAmount);

                                    //Save transactions
                                    mDatabase.child(txtPayableTo.getText().toString()).child("Wallet").child("Transactions").child("Pay").child(mAuth.getUid()).child(txtConfirm.getText().toString()).child("transactionDate").setValue(strDate);
                                    mDatabase.child(txtPayableTo.getText().toString()).child("Wallet").child("Transactions").child("Pay").child(mAuth.getUid()).child(txtConfirm.getText().toString()).child("amount").setValue(txtAmount.getText().toString());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}