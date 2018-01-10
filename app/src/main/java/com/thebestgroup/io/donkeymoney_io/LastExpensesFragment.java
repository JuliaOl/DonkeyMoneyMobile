package com.thebestgroup.io.donkeymoney_io;

import android.annotation.TargetApi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class LastExpensesFragment extends Fragment {

    public LastExpensesFragment() {
        // Required empty public constructor
    }

    @TargetApi(24)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_last_expenses, container,
                false);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        String curr_month = dateFormat.format(new Date());

        TextView mnth = rootView.findViewById(R.id.month);
        mnth.setText(curr_month);

        ListView list = rootView.findViewById(R.id.list_exp);
        list.setAdapter(new ExpensesListAdapter(null, rootView)); //lista operacji

        return rootView;
    }

}
