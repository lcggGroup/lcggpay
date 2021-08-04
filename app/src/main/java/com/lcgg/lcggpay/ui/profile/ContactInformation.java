package com.lcgg.lcggpay.ui.profile;

import android.content.Intent;
import android.os.Bundle;
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
                        fName.setVisibility(View.GONE);
                        mName.setVisibility(View.GONE);
                        lName.setVisibility(View.GONE);

                        fNameEdit.setVisibility(View.VISIBLE);
                        mNameEdit.setVisibility(View.VISIBLE);
                        lNameEdit.setVisibility(View.VISIBLE);

                        edit.setText("Edit");
                        save.setVisibility(View.GONE);
                        Toast.makeText(ContactInformation.this, "Cancel" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName.setVisibility(View.VISIBLE);
                mName.setVisibility(View.VISIBLE);
                lName.setVisibility(View.VISIBLE);

                fNameEdit.setVisibility(View.GONE);
                mNameEdit.setVisibility(View.GONE);
                lNameEdit.setVisibility(View.GONE);

                save.setVisibility(View.GONE);
                edit.setText("Edit");
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fName.setVisibility(View.GONE);
                        mName.setVisibility(View.GONE);
                        lName.setVisibility(View.GONE);

                        fNameEdit.setVisibility(View.VISIBLE);
                        mNameEdit.setVisibility(View.VISIBLE);
                        lNameEdit.setVisibility(View.VISIBLE);

                        edit.setText("Edit");
                        save.setVisibility(View.GONE);
                        Toast.makeText(ContactInformation.this, "Cancel" , Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}