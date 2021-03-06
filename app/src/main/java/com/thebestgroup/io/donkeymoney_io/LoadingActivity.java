package com.thebestgroup.io.donkeymoney_io;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This activity is used to check if client is logged in and starts suitable action.
 */
public class LoadingActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private Boolean isLogged = false;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        new isSignedUp().execute();
        context = this;

    }

    private void startMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    private class isSignedUp extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        /**
         * Checks if user is logged and sets field isLogged.
         * @param voids
         */
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
                if(UserData.readFromFile(context).getAuthorizationToken() != null){
                    isLogged = true;
                }
                else {
                    isLogged = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                isLogged = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Starts Main Activity or Authentication Activity based on isLogged field.
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.GONE);
            if (isLogged)
                startMainActivity();
            else
            startAuthenticationActivity();
        }
    }


    /**
     * starts Login Activity
     */
    private void startAuthenticationActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
