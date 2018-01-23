package com.thebestgroup.io.donkeymoney_io;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_settings, container,
                false);

        Button btn = (Button) rootView.findViewById(R.id.contact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"donkeymoneyapp@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT, "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button logoutbtn = rootView.findViewById(R.id.logout_btn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UserData user = UserData.readFromFile(getContext());
                    user.setAuthorizationToken(null);
                    user.saveToFile(getContext());
                    startActivity(new Intent(getContext(), MainActivity.class));
//                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}
