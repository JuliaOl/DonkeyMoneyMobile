package com.thebestgroup.io.donkeymoney_io;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

public class RegularBudgetFragment extends Fragment {

    public RegularBudgetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_regular_budget, container,
                false);

        Button outbtn = rootView.findViewById(R.id.mexp_btn);
        Button inbtn = rootView.findViewById(R.id.minc_btn);
        Button savbtn = rootView.findViewById(R.id.sav_btn);
        Button budbtn = rootView.findViewById(R.id.mbud_btn);

        final SharedPreferences sp = this.getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        outbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText m_exp = rootView.findViewById(R.id.month_exp);
                float exp_nb = Float.valueOf(m_exp.getText().toString());
                editor.putFloat("monthly expenses", exp_nb);
                editor.commit();
                TextView tve = rootView.findViewById(R.id.display_m);
                tve.setText("Your monthly expenses are set!\n");
            }
        });
        savbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText sav = rootView.findViewById(R.id.month_sav);
                float sav_nb = Float.valueOf(sav.getText().toString());
                editor.putFloat("monthly savings", sav_nb);
                editor.commit();
                TextView tvi = rootView.findViewById(R.id.display_m);
                tvi.setText("Your planned savings are set!\n");
            }
        });
        inbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText m_inc = rootView.findViewById(R.id.month_inc);
                float inc_nb = Float.valueOf(m_inc.getText().toString());
                editor.putFloat("monthly income", inc_nb);
                editor.commit();
                TextView tvi = rootView.findViewById(R.id.display_m);
                tvi.setText("Your monthly income is set!\n");
            }
        });
        budbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               float income = sp.getFloat("monthly income", 0);
               float expense = sp.getFloat("monthly expenses", 0);
               float savings = sp.getFloat("monthly savings", 0);
               float budget = income - expense - savings;
               TextView bud = rootView.findViewById(R.id.m_bud);
               bud.setText(String.format("%.2f",budget));
            }
        });

    // Inflate the layout for this fragment
        return rootView;

    }


}
