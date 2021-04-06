package com.lcgg.lcggpay.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.lcgg.lcggpay.R;

public class ProfileFragment extends AppCompatActivity {

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
}