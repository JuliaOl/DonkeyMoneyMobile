package com.thebestgroup.io.donkeymoney_io;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class LoadingActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private int mLongAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        new isSignedUp().execute();

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
            //startAnimation();
        }

        //tu będzie sprawdzenie czy uzytkownik jest zalogowany
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.GONE);
            //jesli doInBackground zwroci nam true
            //startMainActivity();

            //jesli false
            startAuthenticationActivity();
        }
    }

    private void startAuthenticationActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
