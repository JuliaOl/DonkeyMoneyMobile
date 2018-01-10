package com.thebestgroup.io.donkeymoney_io;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Locale;

@TargetApi(24)
public class AddNewExpenseFragment extends Fragment {
    String dateFormat = "dd.MM.yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);

    public AddNewExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_new_expense, container,
                false);

        EditText editDate = rootView.findViewById(R.id.editDate);
        // init - set date to current date
        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        editDate.setText(dateString);

        // onclick - popup datepicker
        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_new_expense, container, false);
        return rootView;
    }



}
