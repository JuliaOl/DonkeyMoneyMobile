package com.thebestgroup.io.donkeymoney_io;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = new LoginFragment();

            //firstFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, loginFragment).commit();

    }

    public void openSignUp(View view) {
    // Create fragment and give it an argument specifying the article it should show
        SignUpFragment signUpFragment = new SignUpFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, signUpFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
