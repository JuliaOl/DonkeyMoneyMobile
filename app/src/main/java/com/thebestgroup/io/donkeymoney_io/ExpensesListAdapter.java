package com.thebestgroup.io.donkeymoney_io;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Julia on 10.01.2018.
 */

public class ExpensesListAdapter extends BaseAdapter{
    private ArrayList<ListModel> list;
    private Context context;
    private static LayoutInflater inflater = null;
    ListModel tempValues = null;

    ExpensesListAdapter(ArrayList exp_list, Context c) {
        list = exp_list;
        context = c;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ListModel getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder{

        public TextView exp;
        public TextView dat;
        public TextView desc;

    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        View vi = view;
        ViewHolder holder;
        if (view == null) {
            vi = inflater.inflate(R.layout.list_row_last_expenses, null);
            holder = new ViewHolder();
            holder.exp = vi.findViewById(R.id.expense);
            holder.dat = vi.findViewById(R.id.date);
            holder.desc = vi.findViewById(R.id.description);
            vi.setTag(holder);
        }
        else
            holder = (ViewHolder)vi.getTag();

        if (list.size() == 0) {
            holder.desc.setText("no data");
        }
        else {
            tempValues = getItem(pos);
            String cost = tempValues.getExpense();
            holder.exp.setText(cost.concat(" PLN"));
            holder.dat.setText(tempValues.getDate());
            holder.desc.setText(tempValues.getDescription());
        }
        return vi;
    }
}
