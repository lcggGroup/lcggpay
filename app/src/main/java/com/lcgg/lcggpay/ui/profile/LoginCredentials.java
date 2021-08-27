package com.lcgg.lcggpay.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.lcgg.lcggpay.Login;
import com.lcgg.lcggpay.R;

public class LoginCredentials extends AppCompatActivity {
    Button edit;
    Button save;

    EditText passEdit;
    EditText rePassEdit;

    TextView pass;
    TextView rePass;

    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_credential);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        save = findViewById(R.id.loginBtn_save);
        edit = findViewById(R.id.loginBtn_pass);

        pass = findViewById(R.id.txt_login_cred_pass);
        rePass = findViewById(R.id.txt_login_cred_re_pass);
        passEdit = findViewById(R.id.txt_login_cred_passEdit);
        rePassEdit = findViewById(R.id.txt_login_cred_re_passEdit);

        save.setVisibility(View.GONE);

        pass.setVisibility(View.GONE);
        rePass.setVisibility(View.GONE);
        rePassEdit.setVisibility(View.GONE);
        passEdit.setVisibility(View.GONE);

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

                    save.setVisibility(View.GONE);
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
}