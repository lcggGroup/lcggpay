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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.lcgg.lcggpay.Login;
import com.lcgg.lcggpay.R;

import org.w3c.dom.Text;

public class ContactInformation extends AppCompatActivity {
    Button edit;
    Button save;

    TextView fName;
    TextView mName;
    TextView lName;

    EditText fNameEdit;
    EditText mNameEdit;
    EditText lNameEdit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit = findViewById(R.id.contactBtn_edit);
        save = findViewById(R.id.contactBtn_save);

        fName = findViewById(R.id.db_contact_fn);
        mName = findViewById(R.id.db_contact_mn);
        lName = findViewById(R.id.db_contact_ln);

        fNameEdit = findViewById(R.id.txt_contact_ln);
        mNameEdit = findViewById(R.id.txt_contact_mn);
        lNameEdit = findViewById(R.id.txt_contact_ln);

        fName.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        lName.setVisibility(View.VISIBLE);

        fNameEdit.setVisibility(View.GONE);
        mNameEdit.setVisibility(View.GONE);
        lNameEdit.setVisibility(View.GONE);

        save.setVisibility(View.GONE);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName.setVisibility(View.GONE);
                mName.setVisibility(View.GONE);
                lName.setVisibility(View.GONE);

                fNameEdit.setVisibility(View.VISIBLE);
                mNameEdit.setVisibility(View.VISIBLE);
                lNameEdit.setVisibility(View.VISIBLE);

                save.setVisibility(View.VISIBLE);

                edit.setText("Cancel");
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(edit.getText() == "Edit"){

                            fName.setVisibility(View.GONE);
                            mName.setVisibility(View.GONE);
                            lName.setVisibility(View.GONE);

                            fNameEdit.setVisibility(View.VISIBLE);
                            mNameEdit.setVisibility(View.VISIBLE);
                            lNameEdit.setVisibility(View.VISIBLE);

                            save.setVisibility(View.VISIBLE);
                            edit.setVisibility(View.VISIBLE);
                            edit.setText("Cancel");
                        }
                        else {
                            //Cancel
                            fNameEdit.setText("");
                            lNameEdit.setText("");
                            fNameEdit.setError(null);
                            lNameEdit.setError(null);
                            fNameEdit.setBackgroundResource(R.drawable.txt_design_box);
                            lNameEdit.setBackgroundResource(R.drawable.txt_design_box);

                            fNameEdit.setVisibility(View.GONE);
                            mNameEdit.setVisibility(View.GONE);
                            lNameEdit.setVisibility(View.GONE);

                            fName.setVisibility(View.VISIBLE);
                            mName.setVisibility(View.VISIBLE);
                            lName.setVisibility(View.VISIBLE);

                            save.setVisibility(View.GONE);
                            edit.setVisibility(View.VISIBLE);
                            edit.setText("Edit");
                        }


                    }
                });

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(fNameEdit.getText().toString())) {
                    fNameEdit.setError("Enter your First Name");
                    fNameEdit.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (!TextUtils.isEmpty(fNameEdit.getText().toString())) {
                    fNameEdit.setBackgroundResource(R.drawable.txt_design_box);
                }

                if (TextUtils.isEmpty(lNameEdit.getText().toString())) {
                    lNameEdit.setError("Enter your First Name");
                    lNameEdit.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (!TextUtils.isEmpty(lNameEdit.getText().toString())) {
                    lNameEdit.setBackgroundResource(R.drawable.txt_design_box);
                }

                if(!TextUtils.isEmpty(fNameEdit.getText().toString()) &&
                        !TextUtils.isEmpty(lNameEdit.getText().toString())) {

                    fName.setVisibility(View.VISIBLE);
                    mName.setVisibility(View.VISIBLE);
                    lName.setVisibility(View.VISIBLE);

                    fNameEdit.setVisibility(View.GONE);
                    mNameEdit.setVisibility(View.GONE);
                    lNameEdit.setVisibility(View.GONE);

                    save.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                    edit.setText("Edit");
                }
            }
        });
    }
}