package com.thebestgroup.io.donkeymoney_io;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MainPanelFragment mainPanelFragment = new MainPanelFragment();

        getSupportFragmentManager().beginTransaction()
              .add(R.id.main_fragments_container, mainPanelFragment).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fragmentClass = MainPanelFragment.class;
        } else if (id == R.id.nav_gallery) {
            fragmentClass = RegularBudgetFragment.class;
        } else if (id == R.id.nav_slideshow) {
            fragmentClass = OverviewFragment.class;
        } else if (id == R.id.nav_manage) {
            fragmentClass = LastExpensesFragment.class;
        } else if (id == R.id.nav_share) {
            fragmentClass = SettingsFragment.class;
        } else if (id == R.id.nav_send) {
            fragmentClass = AboutUserFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragments_container, fragment).addToBackStack(null).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addExpense(View view) {
        AddNewExpenseFragment newExpenseFragment = new AddNewExpenseFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragments_container, newExpenseFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addIncome(View view) {
        AddNewIncomeFragment newIncomeFragment = new AddNewIncomeFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragments_container, newIncomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void logOut(View view) {
        new UserData().saveToFile(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


}
