package com.lcgg.lcggpay.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lcgg.lcggpay.R;

public class ProfileFragment extends Fragment {

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_view, mobileArray);

        ListView listView = (ListView) root.findViewById(R.id.simpleListView);
        listView.setAdapter(adapter);

        return root;
    }
}