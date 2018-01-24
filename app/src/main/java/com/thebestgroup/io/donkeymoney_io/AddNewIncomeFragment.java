package com.thebestgroup.io.donkeymoney_io;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thebestgroup.io.donkeymoney_io.utils.APIService;
import com.thebestgroup.io.donkeymoney_io.utils.APIUtils;
import com.thebestgroup.io.donkeymoney_io.utils.model.SimpleOperationRequest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@TargetApi(24)
public class AddNewIncomeFragment extends Fragment {
    Context context = getActivity();
    String dateFormat = "dd.MM.yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);

    public AddNewIncomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_new_income, container,
                false);

        final Button addbtn2 = rootView.findViewById(R.id.addbtn2);
        final EditText amount = rootView.findViewById(R.id.add_inc);
        EditText editDate = rootView.findViewById(R.id.editDate);

        long currentdate = System.currentTimeMillis();
        String dateString = sdf.format(currentdate);
        editDate.setText(dateString);

        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        amount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    addbtn2.setActivated(false);
                } else {
                    addbtn2.setActivated(true);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addbtn2.isActivated()){
                    Toast.makeText(getContext(), "Add your income!",Toast.LENGTH_LONG).show();
                } else {
                    String amountS = amount.getText().toString();
                    Double a = new Double(0);
                    try {
                        a = Double.valueOf(amountS);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Log.i("wrong amount", e.toString());
                    }
                    EditText decs = rootView.findViewById(R.id.desc_inc);
                    String description = decs.getText().toString();

                    DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                    Date dateObject;
                    String formatted = "";
                    String time = "";
                    try {
                        EditText usrdate = rootView.findViewById(R.id.editDate);
                        String userdate = usrdate.getText().toString();
                        dateObject = formatter.parse(userdate);
                        formatted = new SimpleDateFormat("yyyy-MM-dd").format(dateObject);
                        time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                        Log.i("wrong date", e.toString());
                    }
                    String finaldate = formatted + "T" + time + ".000Z";

                    APIService service = APIUtils.getAPIService();
                    String header = null;
                    try {
                        header = UserData.readFromFile(getContext()).getAuthorizationToken();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", header);
                    headers.put("Content-Type", "application/json");
                    service.addOperation(
                            headers, new SimpleOperationRequest(description, finaldate, a))
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.code() == 200) {
                                        System.out.println("dodano pomyslnie");
                                        Toast.makeText(getContext(), "Income added succesfully!", Toast.LENGTH_LONG).show();
                                    } else {
                                        System.out.println("tu inny kod niz 200 dla dodanie operacji: " + response.code());
                                        System.out.println("details" + response.message());
                                        System.out.println("details" + response.body());
                                    }
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    System.out.println("Failure");
                                    System.out.println(t);
                                }
                            });
                    if (a == 0) {
                        Toast.makeText(getContext(), "Enter your expense!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return rootView;
    }
}
