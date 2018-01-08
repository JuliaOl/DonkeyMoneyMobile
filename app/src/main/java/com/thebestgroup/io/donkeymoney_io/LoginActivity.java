package com.thebestgroup.io.donkeymoney_io;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    UserData user = new UserData();
    Button signIn;
    EditText loginEdit;
    EditText passwdEdit;

    Button doSignUpBotton;
    EditText nameEdit;
    EditText lastNameEdit;
    EditText passwdSUEdit;
    EditText emailEdit;


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



    public void saveData(View view) {
        doSignUpBotton = (Button)findViewById(R.id.doSignUpButton);
        nameEdit   = (EditText)findViewById(R.id.name);
        lastNameEdit   = (EditText)findViewById(R.id.lastName);
        emailEdit   = (EditText)findViewById(R.id.email);
        passwdSUEdit = (EditText)findViewById(R.id.passwdSignUp);

        //doSignUpBotton.setOnClickListener(
        //        new View.OnClickListener()
        //        {
        //            public void onClick(View view)
        //            {
                        user.setName(nameEdit.getText().toString());
                        user.setLastName(lastNameEdit.getText().toString());
                        user.setEmail(emailEdit.getText().toString());
                        user.setPassword(passwdSUEdit.getText().toString());
                        System.out.println(user.getName()+" "+user.getLastName()+" "+user.getEmail()+" "+user.getPassword());
         //           }
         //       });
        // TODO zarejestuj i wejdź do toMainActivity;
    }

    public void toMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    //to było wykorzystywane do przejścia
    public void login(View view) {
        //if zweryfikowane_dane = ok
        signIn = (Button)findViewById(R.id.signInButton);
        loginEdit   = (EditText)findViewById(R.id.login);
        passwdEdit = (EditText)findViewById(R.id.password);

        signIn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        user.setEmail(loginEdit.getText().toString());
                        user.setPassword(passwdEdit.getText().toString());
                        System.out.println(user.getEmail()+" "+user.getPassword());
                    }
                });
        //else display "login lub hasło niepoprawne
    }
}
