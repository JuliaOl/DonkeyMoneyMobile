package com.thebestgroup.io.donkeymoney_io;

/**
 * Class used to list last expenses
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