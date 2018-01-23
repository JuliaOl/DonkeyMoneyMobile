package com.thebestgroup.io.donkeymoney_io;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thebestgroup.io.donkeymoney_io.utils.APIService;
import com.thebestgroup.io.donkeymoney_io.utils.APIUtils;
import com.thebestgroup.io.donkeymoney_io.utils.SecurityTokenService;
import com.thebestgroup.io.donkeymoney_io.utils.model.LoginResponse;
import com.thebestgroup.io.donkeymoney_io.utils.model.SecurityTokenResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        doSignUpBotton = (Button) findViewById(R.id.doSignUpButton);
        nameEdit = (EditText) findViewById(R.id.name);
        lastNameEdit = (EditText) findViewById(R.id.lastName);
        emailEdit = (EditText) findViewById(R.id.email);
        passwdSUEdit = (EditText) findViewById(R.id.passwdSignUp);

        //doSignUpBotton.setOnClickListener(
        //        new View.OnClickListener()
        //        {
        //            public void onClick(View view)
        //            {
        user.setName(nameEdit.getText().toString());
        user.setLastName(lastNameEdit.getText().toString());
        user.setEmail(emailEdit.getText().toString());
        user.setPassword(passwdSUEdit.getText().toString());
        System.out.println(user.getName() + " " + user.getLastName() + " " + user.getEmail() + " " + user.getPassword());
        //           }
        //       });
        // TODO zarejestuj i wejdź do toMainActivity;
    }

    public void toMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    //to było wykorzystywane do przejścia
    public void login(View view) {
        //if zweryfikowane_dane = ok
        signIn = (Button) findViewById(R.id.signInButton);
        loginEdit = (EditText) findViewById(R.id.login);
        passwdEdit = (EditText) findViewById(R.id.password);

        signIn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        user.setEmail(loginEdit.getText().toString().trim());
                        user.setPassword(passwdEdit.getText().toString());
                        System.out.println(user.getEmail() + " " + user.getPassword());
                        tryToLogin(user);
                    }
                });
    }

    public void tryToLogin(final UserData user) {
        //uzyskiwanie tokenu
        final SecurityTokenService tokenService = APIUtils.getTokenService();
        final APIService service = APIUtils.getAPIService();

        Map<String, String> requestBody = new HashMap<>();

        user.setEmail("donkeymoneyapp@gmail.com");
        user.setPassword("12345678");
        requestBody.put("email", user.getEmail());
        requestBody.put("password", user.getPassword());

//

        final Context context = this;
        tokenService.getSecurityToken(requestBody)
                .enqueue(new Callback<SecurityTokenResponse>() {
                    @Override
                    public void onResponse(Call<SecurityTokenResponse> call, Response<SecurityTokenResponse> response) {
                        if (response.code() == 200) {

                            user.setSecurityToken(response.body().getSecurityToken());
                            System.out.println("securityToken: " + user.getSecurityToken());
                            //logowanie
                            service.getLoginResponse(
                                    "password",
                                    "3MVG9I5UQ_0k_hTmeUVaC9dV..7VgXlT69Oraw3ycdvmAmmiykCsDVWLaJFImgV6lJi2M6BhU8Y0mQvA7WINR",
                                    "6219607681359612175",
                                    user.getEmail(),
                                    user.getPassword() + user.getSecurityToken()
                            ).enqueue(new Callback<LoginResponse>() {
                                @Override
                                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                    if (response.code() == 200) {
                                        user.setAuthorizationToken(response.body().getTokenType() + " " + response.body().getAccessToken());

                                        //zapisywanie do
                                        final SharedPreferences sp = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
                                        final SharedPreferences.Editor editor = sp.edit();
                                        System.out.println("saving to SP...");

                                        editor.putString("authToken", user.getAuthorizationToken());
                                        editor.commit();
                                        System.out.println("saved to SP");

                                        user.setBalance(0.0);
                                        user.saveUserData(LoginActivity.this);

                                        System.out.println("------------- sales force login response --------------");
                                        System.out.println("Authorization " + String.valueOf(response.code()));
                                        System.out.println("Access token " + response.body().getAccessToken());
                                        System.out.println("Token type " + response.body().getTokenType());

                                        toMainActivity();
                                    } else {
                                        System.out.println("tu inny kod niz 200 dla zapytania o token autoryzacji: " + response.code());
                                        System.out.println("details" + response.message());
                                        System.out.println("details" + response.body());
                                    }
//                APIUtils.accessToken = response.body().getTokenType() + " " + response.body().getAccessToken();
                                }

                                @Override
                                public void onFailure(Call<LoginResponse> call, Throwable t) {
                                    System.out.println("Authorization failed");
                                    System.out.println(t);
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<SecurityTokenResponse> call, Throwable t) {
                        System.out.println("uops!");
                        System.out.println(call);
                        System.out.println(t);
                    }
                });

        //else display "login lub hasło niepoprawne
    }
}
