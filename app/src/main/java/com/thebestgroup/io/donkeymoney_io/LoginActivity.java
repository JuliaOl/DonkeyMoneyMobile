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

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Login Fragment is used to log in to the application.
 */
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
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, loginFragment).commit();
    }


    /**
     * Opens a fragment to sign up
     * @param view
     */
    public void openSignUp(View view) {
        // Create fragment and give it an argument specifying the article it should show
        SignUpFragment signUpFragment = new SignUpFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, signUpFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    /**
     * Saves information written by user in textfields to an user object fields when they try to sign up
     * @param view
     */
    public void saveData(View view) {
        doSignUpBotton = (Button) findViewById(R.id.doSignUpButton);
        nameEdit = (EditText) findViewById(R.id.name);
        lastNameEdit = (EditText) findViewById(R.id.lastName);
        emailEdit = (EditText) findViewById(R.id.email);
        passwdSUEdit = (EditText) findViewById(R.id.passwdSignUp);

        user.setName(nameEdit.getText().toString());
        user.setLastName(lastNameEdit.getText().toString());
        user.setEmail(emailEdit.getText().toString());
        user.setPassword(passwdSUEdit.getText().toString());
    }

    /**
     * starts MainActivity
     */
    public void toMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    /**
     * Saves information written by user in text fields to an user object fields and begins login action.
     * @param view
     */
    public void login(View view) {
        signIn = (Button) findViewById(R.id.signInButton);
        loginEdit = (EditText) findViewById(R.id.login);
        passwdEdit = (EditText) findViewById(R.id.password);

        final Context context = this;
        signIn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        user.setEmail(loginEdit.getText().toString().trim());
                        user.setPassword(passwdEdit.getText().toString());

                        if (user.getEmail().length() == 0 || user.getPassword().length() == 0) {
                            Toast.makeText(context, "Fields cannot be empty, you Donkey! ;)", Toast.LENGTH_SHORT).show();
                        } else {
                            tryToLogin(user);
                        }
                    }
                });
    }

    /**
     * Begins and executes authorization process
     * @param user
     */
    public void tryToLogin(final UserData user) {
        final SecurityTokenService tokenService = APIUtils.getTokenService();
        final APIService service = APIUtils.getAPIService();

        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("email", user.getEmail());
        requestBody.put("password", user.getPassword());

        final Context context = this;
        tokenService.getSecurityToken(requestBody)
                .enqueue(new Callback<SecurityTokenResponse>() {
                    @Override
                    public void onResponse(Call<SecurityTokenResponse> call, Response<SecurityTokenResponse> response) {
                        if (response.code() == 200) {
                            Toast.makeText(context, "Logging in...", Toast.LENGTH_SHORT).show();

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

                                        final SharedPreferences sp = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
                                        final SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("authToken", user.getAuthorizationToken());
                                        editor.commit();

                                        user.setBalance(0.0);
                                        user.saveUserData(LoginActivity.this);

                                        //przejście do Main Activity
                                        toMainActivity();
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginResponse> call, Throwable t) {
                                }
                            });
                        } else {
                            Toast.makeText(context, "Wrong login or password", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<SecurityTokenResponse> call, Throwable t) {
                    }
                });
    }
}
