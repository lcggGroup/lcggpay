package com.lcgg.lcggpay.ui.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lcgg.lcggpay.Login;
import com.lcgg.lcggpay.Profile;
import com.lcgg.lcggpay.R;

public class LoginCredentials extends AppCompatActivity {
    private static final String TAG = "Login Credentials";
    Profile profile;
    TextView email;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Button edit;
    Button save;

    EditText passEdit;
    EditText rePassEdit;
    private DatabaseReference myRef;
    TextView pass;
    TextView rePass;

    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_credential);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference(mAuth.getUid());

        save = findViewById(R.id.loginBtn_save);
        edit = findViewById(R.id.loginBtn_pass);

        //db_login_cred_email
        email = findViewById(R.id.db_login_cred_email);

        pass = findViewById(R.id.txt_login_cred_pass);
        rePass = findViewById(R.id.txt_login_cred_re_pass);
        passEdit = findViewById(R.id.txt_login_cred_passEdit);
        rePassEdit = findViewById(R.id.txt_login_cred_re_passEdit);

        save.setVisibility(View.GONE);

        pass.setVisibility(View.GONE);
        rePass.setVisibility(View.GONE);
        rePassEdit.setVisibility(View.GONE);
        passEdit.setVisibility(View.GONE);

        checkUser();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pass.setVisibility(View.VISIBLE);
                rePass.setVisibility(View.VISIBLE);
                rePassEdit.setVisibility(View.VISIBLE);
                passEdit.setVisibility(View.VISIBLE);

                //edit.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);

                edit.setText("Cancel");
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(edit.getText() == "Cancel"){
                            passEdit.setText("");
                            rePassEdit.setText("");
                            passEdit.setError(null);
                            rePassEdit.setError(null);
                            passEdit.setBackgroundResource(R.drawable.txt_design_box);
                            rePassEdit.setBackgroundResource(R.drawable.txt_design_box);

                            edit.setText("Change Password");
                            save.setVisibility(View.GONE);

                            pass.setVisibility(View.GONE);
                            rePass.setVisibility(View.GONE);
                            rePassEdit.setVisibility(View.GONE);
                            passEdit.setVisibility(View.GONE);
                        }
                        else{
                            edit.setText("Cancel");
                            save.setVisibility(View.VISIBLE);

                            pass.setVisibility(View.VISIBLE);
                            rePass.setVisibility(View.VISIBLE);
                            rePassEdit.setVisibility(View.VISIBLE);
                            passEdit.setVisibility(View.VISIBLE);
                        }
                    }
                });


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(passEdit.getText().toString())) {
                    passEdit.setError("Enter your password");
                    passEdit.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (!TextUtils.isEmpty(passEdit.getText().toString())) {
                    passEdit.setBackgroundResource(R.drawable.txt_design_box);
                }

                if (passEdit.getText().toString().length() < 6 ) {
                    passEdit.setError("Minimum length is 7.");
                    passEdit.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (passEdit.getText().toString().length() >= 6 ) {
                    passEdit.setBackgroundResource(R.drawable.txt_design_box);
                }

                if (TextUtils.isEmpty(rePassEdit.getText().toString())) {
                    rePassEdit.setError("Confirm password must not be empty");
                    rePassEdit.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (!TextUtils.isEmpty(rePassEdit.getText().toString())) {
                    rePassEdit.setBackgroundResource(R.drawable.txt_design_box);
                }

                if (!passEdit.getText().toString().equals(rePassEdit.getText().toString())) {
                    rePassEdit.setError("Your password must be the same with your confirmed password");
                    rePassEdit.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (passEdit.getText().toString().equals(rePassEdit.getText().toString()) &&
                        !TextUtils.isEmpty(passEdit.getText().toString()) &&
                        !TextUtils.isEmpty(passEdit.getText().toString())) {
                    rePassEdit.setBackgroundResource(R.drawable.txt_design_box);
                }


                if(!TextUtils.isEmpty(passEdit.getText().toString()) &&
                        passEdit.getText().toString().length() >= 6 &&
                        !TextUtils.isEmpty(rePassEdit.getText().toString()) &&
                        passEdit.getText().toString().equals(rePassEdit.getText().toString())) {

                    updatePassword(passEdit.getText().toString());

                    edit.setVisibility(View.VISIBLE);
                    edit.setText("Change Password");

                    pass.setVisibility(View.GONE);
                    rePass.setVisibility(View.GONE);
                    rePassEdit.setVisibility(View.GONE);
                    passEdit.setVisibility(View.GONE);
                }
            }
        });
    }

    public void checkUser () {
        myRef.child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile = snapshot.getValue(Profile.class);

                email.setText(profile.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void updatePassword (String password) {
        AuthCredential credential = EmailAuthProvider.getCredential(email.getText().toString(), password);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    alertMessage("Password updated");
                }
                else{
                    alertMessage("Error password not updated");
                }
            }
        });
    }

    public void alertMessage (String alertMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginCredentials.this);
        builder.setTitle("Login Credentials");
        builder.setMessage(alertMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // You don't have to do anything here if you just
                // want it dismissed when clicked
            }
        });
        builder.create().show();
    }
}