package com.thebestgroup.io.donkeymoney_io;

import android.content.Context;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainPanelFragment extends Fragment {

    public MainPanelFragment() {
        // Required empty public constructor
}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

                            balance.setText(sum.toString());
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
