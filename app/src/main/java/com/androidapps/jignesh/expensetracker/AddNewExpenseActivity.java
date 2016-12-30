package com.androidapps.jignesh.expensetracker;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidapps.jignesh.expensetracker.model.Expense;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

import static java.text.DateFormat.getDateInstance;

/*http://stackoverflow.com/questions/5357455/limit-decimal-places-in-android-edittext*/
public class AddNewExpenseActivity extends ActionBarActivity {

    EditText amtTb;
    TextView tv1,tv2,tv3;
    DecimalFormat df = new DecimalFormat("###.##");
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_expense);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_arrays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        realm = Realm.getInstance(this);

        amtTb = (EditText) findViewById(R.id.txtAmount);
        amtTb.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8 ,2)});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_expense, menu);
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

    public void Save(View view) {

        //EditText amtTb = (EditText) findViewById(R.id.txtAmount);


        double amt = TryParseDouble(String.valueOf(amtTb.getText()));
        //Log.i("AMT " , String.valueOf(amt));
        if(amt == 0)
        {
            Utility.MsbBar(this,"Please enter valid amount.");

            return;
        }


        Spinner category = (Spinner) findViewById(R.id.spinnerCategory);
        //Log.i("Category Selected ",category.getSelectedItem().toString());
        if(category.getSelectedItem().toString().contentEquals("--Select Category (required)--"))
        {
            Utility.MsbBar(this,"Please choose category.");

            return;
        }

        EditText cmt = (EditText) findViewById(R.id.txtComment);


        try
        {
            realm.beginTransaction();

            Expense exp = realm.createObject(Expense.class);
            exp.setId(UUID.randomUUID().toString());
            exp.setAmount(amt);
            exp.setCategory(category.getSelectedItem().toString());
            exp.setComment(cmt.getText().toString());
            //exp.setCreated(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            exp.setCreated(new Date());
            realm.commitTransaction();

            amtTb.setText("");
            category.setSelection(0);
            cmt.setText("");
            Utility.MsbBar(this, "Record Saved");
        }
        catch (Exception ex)
        {
            Utility.MsbBar(this, ex.getMessage());
            return;
        }




    }

    double TryParseDouble(String s)
    {
        double val = 0;
        try
        {
            val = Double.parseDouble(s);
        }
        catch (NumberFormatException ex)
        {
            val = 0;
        }

        return val;
    }

    public void Back(View view)
    {
        this.finish();
    }


}
