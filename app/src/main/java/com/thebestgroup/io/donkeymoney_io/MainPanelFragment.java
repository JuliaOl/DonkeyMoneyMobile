package com.thebestgroup.io.donkeymoney_io;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.thebestgroup.io.donkeymoney_io.utils.APIService;
import com.thebestgroup.io.donkeymoney_io.utils.APIUtils;
import com.thebestgroup.io.donkeymoney_io.utils.model.OperationResponse;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * MainPanelFragment is a default view. It shows user's current balance
 */
public class MainPanelFragment extends Fragment {

    public MainPanelFragment() {
}

    /**
     * Calculates user's current balance and creates view
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main_panel, container,
                false);

        final TextView balance = rootView.findViewById(R.id.sumExpenses);

        APIService service = APIUtils.getAPIService();

        String header = null;
        try {
            header = UserData.readFromFile(getContext()).getAuthorizationToken();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences sp = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);

        header = sp.getString("authToken", "null");

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

                            Double sum = new Double(0);

                            for (OperationResponse operation: operations) {
                                sum += operation.getAmount();
                            }

                            DecimalFormat df = new DecimalFormat();
                            df.setMinimumFractionDigits(2);

                            balance.setText(df.format(sum));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OperationResponse>> call, Throwable t) {
                    }
                });
        return rootView;
    }
}
