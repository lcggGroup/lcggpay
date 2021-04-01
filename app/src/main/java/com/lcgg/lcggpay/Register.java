package com.lcgg.lcggpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("profile");


    EditText txt_reg_username;
    EditText txt_reg_password;
    EditText txt_reg_retype_pass;
    EditText txt_reg_firstName;
    EditText txt_reg_lastName;

    Button btnSubmit;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txt_reg_username = findViewById(R.id.txt_reg_username);
        txt_reg_password = findViewById(R.id.txt_reg_password);
        txt_reg_retype_pass = findViewById(R.id.txt_reg_retype_pass);
        txt_reg_firstName = findViewById(R.id.txt_reg_first_name);
        txt_reg_lastName = findViewById(R.id.txt_reg_last_name);

        btnSubmit = findViewById(R.id.btn_reg_submit);
        btnCancel = findViewById(R.id.btn_reg_cancel);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(txt_reg_username.getText().toString()) ||
                    !isEmailValid(txt_reg_username.getText().toString())) {
                    txt_reg_username.setError("Enter valid email address for your username");
                    txt_reg_username.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (TextUtils.isEmpty(txt_reg_password.getText().toString())) {
                    txt_reg_password.setError("Enter your password");
                    txt_reg_password.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (txt_reg_password.getText().toString().length() < 6 ) {
                    txt_reg_password.setError("Minimum length is 7.");
                    txt_reg_password.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (TextUtils.isEmpty(txt_reg_retype_pass.getText().toString())) {
                    txt_reg_retype_pass.setError("Confirm your password");
                    txt_reg_retype_pass.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (!txt_reg_password.getText().toString().equals(txt_reg_retype_pass.getText().toString())) {
                    txt_reg_password.setError("Your password must be the same with your confirmed password");
                    txt_reg_password.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (TextUtils.isEmpty(txt_reg_firstName.getText().toString())) {
                    txt_reg_firstName.setError("Enter your First Name");
                    txt_reg_firstName.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (TextUtils.isEmpty(txt_reg_lastName.getText().toString())) {
                    txt_reg_lastName.setError("Enter your Last Name");
                    txt_reg_lastName.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (!TextUtils.isEmpty(txt_reg_username.getText().toString()) ||
                        isEmailValid(txt_reg_username.getText().toString()) ||
                    !TextUtils.isEmpty(txt_reg_password.getText().toString()) ||
                    txt_reg_password.getText().toString().length() >= 6 ||
                    !TextUtils.isEmpty(txt_reg_retype_pass.getText().toString()) ||
                    txt_reg_password.getText().toString().equals(txt_reg_retype_pass.getText().toString()) ||
                    !TextUtils.isEmpty(txt_reg_firstName.getText().toString()) ||
                    !TextUtils.isEmpty(txt_reg_lastName.getText().toString())) {

                    txt_reg_username.setBackgroundResource(R.drawable.txt_design_box);
                    txt_reg_password.setBackgroundResource(R.drawable.txt_design_box);
                    txt_reg_password.setBackgroundResource(R.drawable.txt_design_box);
                    txt_reg_retype_pass.setBackgroundResource(R.drawable.txt_design_box);
                    txt_reg_password.setBackgroundResource(R.drawable.txt_design_box);
                    txt_reg_firstName.setBackgroundResource(R.drawable.txt_design_box);
                    txt_reg_lastName.setBackgroundResource(R.drawable.txt_design_box);

                    mAuth.createUserWithEmailAndPassword(txt_reg_username.getText().toString(), txt_reg_password.getText().toString())
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this, R.style.question_dialog);
                                        builder.setTitle("Authentication Failed");
                                        builder.setMessage(task.getException().getMessage());
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // You don't have to do anything here if you just
                                                // want it dismissed when clicked
                                            }
                                        });
                                        builder.create().show();
                                    }
                                    else {
                                        writeNewUser(txt_reg_username.getText().toString(),txt_reg_firstName.getText().toString(),txt_reg_lastName.getText().toString());
                                        writeNewWallet();

                                        startActivity(new Intent(Register.this, MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                }



            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void writeNewUser(String username, String firstName, String lastName) {
        Profile profile = new Profile (username, firstName, lastName);
        mDatabase.child(mAuth.getUid()).child("Profile").setValue(profile);
    }

    public void writeNewWallet(){
        Date date = new Date();
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        String strDate = dateFormat.format(date);

        //Initial Value
        mDatabase.child(mAuth.getUid()).child("Wallet").child("amount").setValue(0.0);

        //Save transactions
        mDatabase.child(mAuth.getUid()).child("Wallet").child("Transactions").child("Register").child("transactionDate").setValue(strDate);
        mDatabase.child(mAuth.getUid()).child("Wallet").child("Transactions").child("Register").child("amount").setValue(0.0);
    }
}