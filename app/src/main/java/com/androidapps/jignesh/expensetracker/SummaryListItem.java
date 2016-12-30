package com.androidapps.jignesh.expensetracker;

/**
 * Created by developer on 4/6/2015.
 */
public class SummaryListItem {

    private Summary title;
    public Summary getTitle(){return title;}
    public void setTitle(Summary _title){this.title = _title;}

    private String amount;
    public String getAmount(){return amount;}
    public void setAmount(String amt){this.amount = amt;}
}
