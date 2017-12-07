package com.thebestgroup.io.donkeymoney_io.authantication.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.SignInButton;
import com.thebestgroup.io.donkeymoney_io.R;


public class LoginFragment extends Fragment {

    GoogleLoginListener googleLoginListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        googleLoginListener = (GoogleLoginListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        SignInButton googleSignIn = view.findViewById(R.id.google_sign_in);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleLoginListener.OnGoogleSignInClicked();
            }
        });

        // Customizing G+ button
        googleSignIn.setSize(SignInButton.SIZE_STANDARD);

        return view;
    }

    public LoginFragment() {
        // Required empty public constructor
    }
}
