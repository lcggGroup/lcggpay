package com.lcgg.lcggpay.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.lcgg.lcggpay.Login;
import com.lcgg.lcggpay.MainActivity;
import com.lcgg.lcggpay.Profile;
import com.lcgg.lcggpay.R;

public class ProfileFragment extends AppCompatActivity {
    Intent intent;
    private FirebaseAuth mAuth;

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    //public View onCreateView(@NonNull LayoutInflater inflater,
                             //ViewGroup container, Bundle savedInstanceState) {

        //View root = inflater.inflate(R.layout.fragment_profile, container, false);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        ArrayAdapter adapter = new ArrayAdapter<String>(ProfileFragment.this, R.layout.list_view, mobileArray);

        ListView listView = (ListView) findViewById(R.id.simpleListView);
        listView.setAdapter(adapter);
        //return root;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.navigation_home:
                //add the function to perform here
                //getSupportFragmentManager().beginTransaction()
                //.replace(R.id.nav_host_fragment, new ProfileFragment())
                //.commit();
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