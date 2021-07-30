package com.lcgg.lcggpay.ui.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.lcgg.lcggpay.Login;
import com.lcgg.lcggpay.R;

public class TransferActivity extends AppCompatActivity {
    Intent intent;
    String[] mobileArray = { "Username and Password","Contact Information" };
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter adapter = new ArrayAdapter<String>(TransferActivity.this, R.layout.list_view, mobileArray);
        ListView listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(adapter);

        //return root;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    //code specific to first list item
                    Toast.makeText(TransferActivity.this, "1" , Toast.LENGTH_SHORT).show();
                    //create intent - UserName and Password
                }
                else if(position == 1) {
                    //code specific to first list item
                    Toast.makeText(TransferActivity.this, "2" , Toast.LENGTH_SHORT).show();
                    //create intent - Contact Information
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu_profile, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.exit:
                //add the function to perform here
                mAuth.getInstance().signOut();
                intent = new Intent(TransferActivity.this, Login.class);
                startActivity(intent);
                finish();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}