package com.thebestgroup.io.donkeymoney_io;

;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

@TargetApi(24)
public class OverviewFragment extends Fragment {

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_overview, container,
                false);

        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MMMM", Locale.ENGLISH);
        String curr_month = dateFormat.format(new Date());

        TextView mnth = rootView.findViewById(R.id.month2);
        mnth.setText(curr_month);

        TextView r_exp = rootView.findViewById(R.id.reg_exp);
        TextView r_inc = rootView.findViewById(R.id.reg_inc);
        TextView sav = rootView.findViewById(R.id.tot_sav);
        TextView bud = rootView.findViewById(R.id.bud_left);

        SharedPreferences sp = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String income = Float.toString(sp.getFloat("monthly income", 0));
        String expense = Float.toString(sp.getFloat("monthly expenses", 0));
        String savings = Float.toString(sp.getFloat("monthly savings", 0));
        Float budget = sp.getFloat("monthly income", 0) - sp.getFloat("monthly expenses", 0) - sp.getFloat("mothly savings", 0);
        r_exp.setText(expense);
        r_inc.setText(income);
        sav.setText(savings);
        bud.setText(budget.toString());

        return rootView;
    }

}
