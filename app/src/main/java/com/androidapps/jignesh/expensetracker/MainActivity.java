package com.androidapps.jignesh.expensetracker;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.util.Currency;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {
    static String CURRENCY_SYMBOL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Locale current = getResources().getConfiguration().locale;
        Currency cur = Currency.getInstance(current);
        CURRENCY_SYMBOL = cur.getCurrencyCode();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToNewExpense(View v)
    {
        Intent intent = new Intent(this, AddNewExpenseActivity.class);
        startActivity(intent);
        //Log.i("Btn Click : ", "go to new expense");
    }

    public void goToExpenseSummary(View v)
    {
        Intent intent = new Intent(this, ViewExpenseActivity.class);
        startActivity(intent);
        //Log.i("Btn Click : ", "go to expense summary");
    }

    public void about(View v)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("About");
        alert.setMessage("This is simple Expense Tracker app. You can add expense, delete expense, and  export csv file to sdcard.");
        alert.setPositiveButton("OK",null);
        alert.show();
    }
}
