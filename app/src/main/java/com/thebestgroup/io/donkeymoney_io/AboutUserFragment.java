package com.thebestgroup.io.donkeymoney_io;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

public class AboutUserFragment extends Fragment {

    public AboutUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_about_user, container,
                false);

        TextView email = rootView.findViewById(R.id.email);
        TextView userName = rootView.findViewById(R.id.usr_name);

        UserData userData = null;
        try {
            userData = UserData.readFromFile(rootView.getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(email);
        email.setText(userData.getEmail());
        userName.setText(userData.getName());
        // Inflate the layout for this fragment
        return rootView;
    }

}
