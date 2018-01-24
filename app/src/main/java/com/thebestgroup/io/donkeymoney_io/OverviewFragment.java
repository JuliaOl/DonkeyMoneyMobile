package com.thebestgroup.io.donkeymoney_io;

;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thebestgroup.io.donkeymoney_io.utils.APIService;
import com.thebestgroup.io.donkeymoney_io.utils.APIUtils;
import com.thebestgroup.io.donkeymoney_io.utils.model.OperationResponse;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@TargetApi(24)
public class OverviewFragment extends Fragment {

    public OverviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_overview, container,
                false);

        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MMMM", Locale.ENGLISH);
        String curr_month = dateFormat.format(new Date());

        TextView mnth = rootView.findViewById(R.id.month2);
        mnth.setText(curr_month);

        final TextView t_exp = rootView.findViewById(R.id.tot_exp);
        final TextView r_exp = rootView.findViewById(R.id.reg_exp);
        final TextView o_exp = rootView.findViewById(R.id.oth_exp);
        final TextView t_inc = rootView.findViewById(R.id.tot_inc);
        final TextView r_inc = rootView.findViewById(R.id.reg_inc);
        final TextView o_inc = rootView.findViewById(R.id.oth_inc);
        final TextView sav = rootView.findViewById(R.id.tot_sav);
        final TextView bud = rootView.findViewById(R.id.bud_left);
        final Activity activity = this.getActivity();

        APIService service = APIUtils.getAPIService();
        String header = null;
        try {
            header = UserData.readFromFile(getContext()).getAuthorizationToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", header);
        service.getOperations(
                headers,
                100)
                .enqueue(new Callback<List<OperationResponse>>() {
                    @Override
                    public void onResponse(Call<List<OperationResponse>> call, Response<List<OperationResponse>> response) {
                        if (response.code() == 200) {
                            List<OperationResponse> operations = response.body();

                            Double oth_expenses = new Double(0);
                            Double oth_income = new Double(0);

                            for (OperationResponse operation: operations) {
                                if (operation.getAmount() > 0) {
                                    oth_income += operation.getAmount();
                                } else {
                                    oth_expenses += operation.getAmount();
                                }
                            }
                            DecimalFormat df = new DecimalFormat("#.00");
                            o_exp.setText(df.format(oth_expenses));
                            o_inc.setText(df.format(oth_income));

                            Float o_expenses = Float.valueOf(String.valueOf(oth_expenses));
                            Float o_income = Float.valueOf(String.valueOf(oth_income));

                            SharedPreferences sp = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();

                            Float r_income = sp.getFloat("monthly income", 0);
                            Float r_expense = - sp.getFloat("monthly expenses", 0);
                            Float savings = sp.getFloat("monthly savings", 0);
                            Float budget = sp.getFloat("monthly income", 0) + o_income - sp.getFloat("monthly expenses", 0) +
                                    o_expenses - sp.getFloat("monthly savings", 0);

                            String result = String.format("%.2f", budget);

                            t_exp.setText(String.format("%.2f",r_expense + o_expenses));
                            r_exp.setText(String.format("%.2f", r_expense));
                            t_inc.setText(String.format("%.2f", r_income + o_income));
                            r_inc.setText(String.format("%.2f", r_income));
                            sav.setText(String.format("%.2f", savings));
                            bud.setText(result);

                        } else {
                            System.out.println("tu inny kod niz 200 dla zapytania o token autoryzacji: " + response.code());
                            System.out.println("details" + response.message());
                            System.out.println("details" + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OperationResponse>> call, Throwable t) {
                        System.out.println("Authorization failed");

                        System.out.println(t);
                    }
                });

        return rootView;
    }

}
