package com.thebestgroup.io.donkeymoney_io.authantication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.thebestgroup.io.donkeymoney_io.MainActivity;
import com.thebestgroup.io.donkeymoney_io.R;
import com.thebestgroup.io.donkeymoney_io.authantication.login.LoginActivity;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar loadingCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        loadingCircle = findViewById(R.id.loading_spinner);
        new IsSignedUp().execute();

    }

    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private class IsSignedUp extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingCircle.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //tu będzie sprawdzenie czy uzytkownik jest zalogowany
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isAuthenticated) {
            super.onPostExecute(false);
            loadingCircle.setVisibility(View.GONE);

            if (isAuthenticated){
                startMainActivity();
            } else {
                startAuthenticationActivity();
            }
        }
    }

    private void startAuthenticationActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
