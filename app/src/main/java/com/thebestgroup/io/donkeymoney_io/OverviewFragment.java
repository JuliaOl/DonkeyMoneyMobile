package com.thebestgroup.io.donkeymoney_io;

;
import android.annotation.TargetApi;
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

        return rootView;
    }

}
