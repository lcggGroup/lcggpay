package com.lcgg.lcggpay.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.lcgg.lcggpay.Login;
import com.lcgg.lcggpay.MainActivity;
import com.lcgg.lcggpay.Profile;
import com.lcgg.lcggpay.R;
import com.lcgg.lcggpay.ui.home.HomeFragment;

public class ProfileFragment extends AppCompatActivity {
    Intent intent;
    private FirebaseAuth mAuth;

    String[] mobileArray = { "Username and Password","Name and Contact Information" };
    String[] mobileArray2 = { "Name", "Contact Information", "QR Code" };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        ArrayAdapter adapter = new ArrayAdapter<String>(ProfileFragment.this, R.layout.list_view, mobileArray);
        ListView listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(adapter);

        //return root;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    //code specific to first list item
                    Toast.makeText(ProfileFragment.this, "1" , Toast.LENGTH_SHORT).show();
                    //create intent - UserName and Password
                }
                else if(position == 1) {
                    //code specific to first list item
                    ArrayAdapter adapter2 = new ArrayAdapter<String>(ProfileFragment.this, R.layout.list_view, mobileArray2);
                    ListView listView2 = (ListView) findViewById(R.id.simpleListView);
                    listView2.setAdapter(adapter2);

                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(position == 0){
                                Toast.makeText(ProfileFragment.this, "1.1" , Toast.LENGTH_SHORT).show();
                                //create intent - Name
                            }
                            else if(position == 1){
                                Toast.makeText(ProfileFragment.this, "1.2" , Toast.LENGTH_SHORT).show();
                                //create intent - Contact Information
                            }
                            else if(position == 2){
                                Toast.makeText(ProfileFragment.this, "1.3" , Toast.LENGTH_SHORT).show();
                                //create intent - QR Code
                            }
                        }
                    });
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
            case R.id.home:
                intent = new Intent(ProfileFragment.this, MainActivity.class);
                startActivity(intent);
                return(true);

            case R.id.exit:
                //add the function to perform here
                mAuth.getInstance().signOut();
                intent = new Intent(ProfileFragment.this, Login.class);
                startActivity(intent);
                finish();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}