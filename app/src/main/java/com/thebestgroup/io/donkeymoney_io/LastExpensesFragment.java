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

import com.thebestgroup.io.donkeymoney_io.utils.APIService;
import com.thebestgroup.io.donkeymoney_io.utils.APIUtils;
import com.thebestgroup.io.donkeymoney_io.utils.model.LoginResponse;
import com.thebestgroup.io.donkeymoney_io.utils.model.OperationResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LastExpensesFragment extends Fragment {

    public LastExpensesFragment() {
        // Required empty public constructor
    }

    @TargetApi(24)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_last_expenses, container,
                false);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        String curr_month = dateFormat.format(new Date());

        TextView mnth = rootView.findViewById(R.id.month);
        mnth.setText(curr_month);


        APIService service = APIUtils.getAPIService();
        String header = UserData.readFromFile(getContext()).getAuthorizationToken();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", header);
        service.getOperations(
                headers,
                10)
                .enqueue(new Callback<List<OperationResponse>>() {
                    @Override
                    public void onResponse(Call<List<OperationResponse>> call, Response<List<OperationResponse>> response) {
                        if (response.code() == 200) {
                            List<OperationResponse> operations = response.body();

                            ListView list = rootView.findViewById(R.id.list_exp);
                            ArrayList<ListModel> lista = new ArrayList<>();

                            for (OperationResponse operation: operations) {
                                ListModel el = new ListModel();
                                el.setDescription(operation.getName());
                                el.setDate(operation.getCreatedAt().substring(0, 10));
                                el.setExpense(operation.getAmount().toString());
                                lista.add(el);
                            }

                            list.setAdapter(new ExpensesListAdapter(lista, rootView.getContext())); //lista operacji

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






