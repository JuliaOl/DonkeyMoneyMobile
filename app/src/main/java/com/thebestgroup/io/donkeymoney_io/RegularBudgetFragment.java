package com.thebestgroup.io.donkeymoney_io;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * This Fragment is responsible for viewing user's monthly budget based on regular expenses, incomes and planned savings.
 */
public class RegularBudgetFragment extends Fragment {

    public RegularBudgetFragment() {
    }

    /**
     * Calculates regular monthly budget.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_regular_budget, container,
                false);

        final EditText m_exp = rootView.findViewById(R.id.month_exp);
        final EditText m_inc = rootView.findViewById(R.id.month_inc);
        final EditText sav = rootView.findViewById(R.id.month_sav);

        final Button outbtn = rootView.findViewById(R.id.mexp_btn);
        final Button inbtn = rootView.findViewById(R.id.minc_btn);
        final Button budbtn = rootView.findViewById(R.id.mbud_btn);

        final SharedPreferences sp = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();

        m_exp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if ((s.toString().trim().length() == 0)) {
                    outbtn.setActivated(false);
                } else {
                    float exp_nb = Float.valueOf(m_exp.getText().toString());
                    editor.putFloat("monthly expenses", exp_nb);
                    editor.commit();
                    outbtn.setActivated(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        m_inc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if ((s.toString().trim().length() == 0)) {
                    inbtn.setActivated(false);
                } else {
                    inbtn.setActivated(true);
                    float inc_nb = Float.valueOf(m_inc.getText().toString());
                    editor.putFloat("monthly income", inc_nb);
                    editor.commit();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sav.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().trim().length() == 0) {
                } else {
                    float sav_nb = Float.valueOf(sav.getText().toString());
                    editor.putFloat("monthly savings", sav_nb);
                    editor.commit();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // calculates regular monthly budget
        budbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sp.getFloat("monthly income", 0) != 0) && (sp.getFloat("monthly expenses", 0) != 0)) {
                    outbtn.setActivated(true); inbtn.setActivated(true);
                } else {
                    TextView tvi = rootView.findViewById(R.id.display_m);
                    tvi.setText("Your expenses and income cannot equal zero!\n");
                }
                if (outbtn.isActivated() && inbtn.isActivated()) {
                    float income = sp.getFloat("monthly income", 0);
                    float expense = sp.getFloat("monthly expenses", 0);
                    float savings = sp.getFloat("monthly savings", 0);
                    float budget = income - expense - savings;
                    TextView bud = rootView.findViewById(R.id.m_bud);
                    bud.setText(String.format("%.2f", budget));
                    TextView tvi = rootView.findViewById(R.id.display_m);
                    tvi.setText("Your monthly budget is set!\n");
                }
            }
        });

        return rootView;
    }

}
