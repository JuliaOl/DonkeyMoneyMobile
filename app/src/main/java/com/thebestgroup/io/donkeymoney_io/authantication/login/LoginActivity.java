package com.thebestgroup.io.donkeymoney_io.authantication.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.thebestgroup.io.donkeymoney_io.MainActivity;
import com.thebestgroup.io.donkeymoney_io.R;
import com.thebestgroup.io.donkeymoney_io.utils.APIService;
import com.thebestgroup.io.donkeymoney_io.utils.ApiUtils;
import com.thebestgroup.io.donkeymoney_io.utils.pojos.AuthorizationResponsePojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity
        extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleLoginListener {

    private static final String TAG = "Authantication";
    private GoogleApiClient googleApiClient;
    private static final int GOOGLE_SIGN_IN = 007;
    private APIService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        GoogleSignInOptions gso = createGso();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        service = ApiUtils.getAPIService();

        LoginFragment loginFragment = new LoginFragment();
        //firstFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, loginFragment).commit();

    }

    public void openSignUp(View view) {
        SignUpFragment signUpFragment = new SignUpFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, signUpFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void login(View view) {
        Log.i("Login", "start");
        service.login(
                "password",
                "3MVG9I5UQ_0k_hTmeUVaC9dV..7VgXlT69Oraw3ycdvmAmmiykCsDVWLaJFImgV6lJi2M6BhU8Y0mQvA7WINR",
                "6219607681359612175",
                "donkeymoneyapp@gmail.com",
                "12345678fCX9cOnMr0HEccp3xWqNZsdpv"
        ).enqueue(new Callback<AuthorizationResponsePojo>() {
            @Override
            public void onResponse(Call<AuthorizationResponsePojo> call, Response<AuthorizationResponsePojo> response) {
                Log.d("Authorization", String.valueOf(response.code()));
                Log.d("Access token", response.body().getAccessToken());
                Log.d("TOken type", response.body().getAccessToken());
                ApiUtils.accessToken = response.body().getTokenType() + " " + response.body().getAccessToken();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<AuthorizationResponsePojo> call, Throwable t) {
                Log.e("Authorization", "Failure");
            }
        });
    }

    private GoogleSignInOptions createGso() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("GOOGLE", "Connection failed");
        Toast.makeText(this, "Connection failed. Check your Internet connection", Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnGoogleSignInClicked() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        Log.d("Authantication", "handleGoogleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());


            Log.d("Email", acct.getEmail());
            Log.d("Name", acct.getDisplayName());
            Log.d("Token", acct.getIdToken());
        }
    }
}
