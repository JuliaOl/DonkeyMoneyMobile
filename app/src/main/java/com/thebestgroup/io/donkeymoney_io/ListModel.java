package com.thebestgroup.io.donkeymoney_io;

/**
 * Created by Julia on 11.01.2018.
 */

public class ListModel {

    private  String expense;
    private  String description;
    private  String date;

    public void setExpense(String expense)
    {
        this.expense = expense;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getExpense()
    {
        return this.expense;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getDate()
    {
        return this.date;
    }
}