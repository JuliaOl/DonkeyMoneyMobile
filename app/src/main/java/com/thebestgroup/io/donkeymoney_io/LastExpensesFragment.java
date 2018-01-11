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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        ArrayList<ListModel> listaTest = new ArrayList<>();
        ListModel el1 = new ListModel();
        ListModel el2 = new ListModel();
        el1.setExpense("100"); el1.setDescription("sukienka"); el1.setDate("10-01-2018");
        el2.setExpense("9"); el2.setDescription("kawa"); el2.setDate("11-01-2018");
        listaTest.add(el1); listaTest.add(el2);
        list.setAdapter(new ExpensesListAdapter(listaTest, rootView.getContext())); //lista operacji

        return rootView;
    }

}
