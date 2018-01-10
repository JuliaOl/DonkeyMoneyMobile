package com.thebestgroup.io.donkeymoney_io;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Julia on 10.01.2018.
 */

public class ExpensesListAdapter extends BaseAdapter{
    private ArrayList expenses;
    View v;
    ExpensesListAdapter(ArrayList exp_list, View view) {
        expenses = exp_list;
        v = view;
    }

    @Override
    public int getCount() {
        return expenses.size();
    }

    @Override
    public Object getItem(int i) {
        return expenses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
