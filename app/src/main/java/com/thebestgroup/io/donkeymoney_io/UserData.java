package com.thebestgroup.io.donkeymoney_io;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;

import com.thebestgroup.io.donkeymoney_io.utils.APIService;
import com.thebestgroup.io.donkeymoney_io.utils.APIUtils;
import com.thebestgroup.io.donkeymoney_io.utils.model.OperationResponse;
import com.thebestgroup.io.donkeymoney_io.utils.model.UserDataResponse;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Zosia on 07.01.2018.
 */

public class UserData implements Serializable {
    public String name;
    public String lastName;
    public String email;
    public String password;
    public String securityToken;
    public String authorizationToken;
    public Double balance;


    // Constant with a file name
    public static String fileName = "userData.ser";

    // Serializes an object and saves it to a file
    public void saveToFile(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.flush();
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // Creates an object by reading it from a file
    public static UserData readFromFile(Context context) throws IOException {
        UserData createResumeForm = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            createResumeForm = (UserData) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return createResumeForm;
    }

    public void saveUserData(final Context context) {

        APIService service = APIUtils.getAPIService();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", authorizationToken);
        service.getUser(headers)
                .enqueue(new Callback<UserDataResponse>() {
                    @Override
                    public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {
                        if (response.code() == 200) {
                            UserDataResponse details = response.body();
                            name = details.getName();
                            lastName = details.getLastName();
                            balance = details.getAccountBalance();
                            System.out.println("balance in getting details: " + balance);
                            saveToFile(context);
                        } else {
                            System.out.println("save user data apytania o token autoryzacji: " + response.code());
                            System.out.println("details" + response.message());
                            System.out.println("details" + response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDataResponse> call, Throwable t) {
                        System.out.println("Authorization failed");

                        System.out.println(t);
                    }
                });

    }

    public static void updateBalance(final Context context) {
        try {
            UserData.readFromFile(context).saveUserData(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}


