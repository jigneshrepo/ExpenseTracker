package com.androidapps.jignesh.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidapps.jignesh.expensetracker.model.Expense;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class ViewExpenseActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "";
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);

        TextView currency = (TextView)findViewById(R.id.currency);
        currency.setText("Your currency : " + MainActivity.CURRENCY_SYMBOL);

        realm = Realm.getInstance(this);

        int enumLength = Summary.values().length;

        double[] amts = new double[enumLength];

        Date dt = null;
        //TODAY
        dt = Utility.GetTodaysDate();
        amts[Summary.TODAY.ordinal()] = realm.where(Expense.class)
                .between("created", dt, new Date())
                .sumDouble("amount");
        //Log.i("TODAY ",String.valueOf(amts[Summary.TODAY.ordinal()]));

        //THIS WEEK
        dt = Utility.GetThisWeekFirstDate();
        String monday = new SimpleDateFormat("yyyy-MM-dd").format(dt);
        //Log.i("MONDAY ", monday);

        amts[Summary.THIS_WEEK.ordinal()] = realm.where(Expense.class)
                .between("created", dt, new Date())
                .sumDouble("amount");
        //Log.i("THIS WEEK ", String.valueOf(amts[Summary.THIS_WEEK.ordinal()]));

        //THIS MONTH
        dt = Utility.GetThisMonthFirstDate();
        //c.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
        String firstDayOfMonth = new SimpleDateFormat("yyyy-MM-dd").format(dt);
        //Log.i("First Day Of Month ", firstDayOfMonth);

        amts[Summary.THIS_MONTH.ordinal()] = realm.where(Expense.class)
                .between("created", dt, new Date())
                .sumDouble("amount");

        //THIS YEAR
        dt = Utility.GetThisYearFirstDate();
        //c.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
        String firstDayOfYear = new SimpleDateFormat("yyyy-MM-dd").format(dt);
        //Log.i("First Day Of Year ", firstDayOfYear);

        amts[Summary.THIS_YEAR.ordinal()] = realm.where(Expense.class)
                .between("created", dt, new Date())
                .sumDouble("amount");


        //ALL
        amts[Summary.ALL.ordinal()] = realm.where(Expense.class)
                .sumDouble("amount");

        dt = null;

        SummaryListItem singleItem;
        ArrayList<SummaryListItem> list = new ArrayList<SummaryListItem>();

        for (int i = 0; i < enumLength ; i++)
        {
            singleItem = new SummaryListItem();
            singleItem.setTitle(Summary.values()[i]);
            singleItem.setAmount(String.valueOf(amts[i]));
            list.add(singleItem);
        }

        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        ListView lView = (ListView)findViewById(R.id.list);
        lView.setAdapter(adapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(ViewExpenseActivity.this, ViewExpenseDetailsActivity.class);
                String message = Summary.values()[position].toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                finish();
                startActivity(intent);


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_expense, menu);
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

    public void Back(View view)
    {
        this.finish();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }
}
