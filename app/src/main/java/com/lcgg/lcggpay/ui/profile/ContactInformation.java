package com.lcgg.lcggpay.ui.profile;

import android.content.DialogInterface;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lcgg.lcggpay.Login;
import com.lcgg.lcggpay.Profile;
import com.lcgg.lcggpay.R;
import com.lcgg.lcggpay.Register;
import com.lcgg.lcggpay.Wallet;

import org.w3c.dom.Text;

public class ContactInformation extends AppCompatActivity {
    Profile profile;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

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

        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference(mAuth.getUid());


        edit = findViewById(R.id.contactBtn_edit);
        save = findViewById(R.id.contactBtn_save);

        fName = findViewById(R.id.db_contact_fn);
        mName = findViewById(R.id.db_contact_mn);
        lName = findViewById(R.id.db_contact_ln);

        fNameEdit = findViewById(R.id.txt_contact_fn);
        mNameEdit = findViewById(R.id.txt_contact_mn);
        lNameEdit = findViewById(R.id.txt_contact_ln);

        fName.setVisibility(View.VISIBLE);
        mName.setVisibility(View.VISIBLE);
        lName.setVisibility(View.VISIBLE);

        fNameEdit.setVisibility(View.GONE);
        mNameEdit.setVisibility(View.GONE);
        lNameEdit.setVisibility(View.GONE);

        save.setVisibility(View.GONE);

        checkUser();

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
                            mNameEdit.setText("");
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
                    lNameEdit.setError("Enter your Last Name");
                    lNameEdit.setBackgroundResource(R.drawable.txt_design_box_red);
                }
                else if (!TextUtils.isEmpty(lNameEdit.getText().toString())) {
                    lNameEdit.setBackgroundResource(R.drawable.txt_design_box);
                }

                if(!TextUtils.isEmpty(fNameEdit.getText().toString()) &&
                        !TextUtils.isEmpty(lNameEdit.getText().toString())) {

                    updateUser(fNameEdit.getText().toString(), mNameEdit.getText().toString(), lNameEdit.getText().toString());

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

    public void checkUser () {
        myRef.child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile = snapshot.getValue(Profile.class);

                fName.setText(profile.getFirstName());
                mName.setText(profile.getMiddleName());
                lName.setText(profile.getLastName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void updateUser (String firstName, String middleName, String lastName){
        profile = new Profile (null, null, firstName, middleName, lastName);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child("Profile").setValue(profile);

                AlertDialog.Builder builder = new AlertDialog.Builder(ContactInformation.this);
                builder.setTitle("Contact Information");
                builder.setMessage("Successfully updated Contact Information.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // You don't have to do anything here if you just
                        // want it dismissed when clicked
                        fNameEdit.setText("");
                        mNameEdit.setText("");
                        lNameEdit.setText("");
                    }
                });
                builder.create().show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}