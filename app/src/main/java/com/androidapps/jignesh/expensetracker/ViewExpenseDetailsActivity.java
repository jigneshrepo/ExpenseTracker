package com.androidapps.jignesh.expensetracker;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.androidapps.jignesh.expensetracker.model.Expense;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/*save data as csv file*/
/*http://stackoverflow.com/questions/5401104/android-exporting-to-csv-and-sending-as-email-attachment*/
public class ViewExpenseDetailsActivity extends ActionBarActivity {

    private ListView lView;
    ArrayList<Expense> list;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense_details);

        realm = Realm.getInstance(this);


        // Get the message from the intent
        Intent intent = getIntent();
        Summary searchBy = Summary.valueOf(intent.getStringExtra(ViewExpenseActivity.EXTRA_MESSAGE));
        //Log.i("SEARCH BY ", searchBy.name());
        TextView tvHeading = (TextView)findViewById(R.id.tvHeading);
        tvHeading.setText(searchBy.toString().replace('_',' '));

        if(searchBy == null) {
            searchBy = Summary.TODAY;
        }

        RealmResults<Expense> result = null;
        Date dt = null;
        switch (searchBy)
        {
            case  TODAY:
                dt = Utility.GetTodaysDate();
                result = realm.where(Expense.class)
                        .between("created", dt, new Date())
                        .findAll();
                break;
            case THIS_WEEK:
                dt = Utility.GetThisWeekFirstDate();
                String monday = new SimpleDateFormat("dd MMM yyyy").format(dt);
                //Log.i("MONDAY ", monday);
                result = realm.where(Expense.class)
                        .between("created", dt, new Date())
                        .findAll();
                break;
            case THIS_MONTH:
                dt = Utility.GetThisMonthFirstDate();
                result = realm.where(Expense.class)
                        .between("created", dt, new Date())
                        .findAll();
                break;
            case THIS_YEAR:
                dt = Utility.GetThisYearFirstDate();

                result = realm.where(Expense.class)
                        .between("created", dt, new Date())
                        .findAll();
                break;
            case ALL:
                result = realm.where(Expense.class)
                        .findAll();
            default:break;
        }

        list = new ArrayList<Expense>();
        if(result != null)
        {
            if(result.size() == 0)
            {
                Utility.MsbBar(this, "No record found");
            }

            for (Expense e : result)
            {
                list.add(e);
            }
        }

        lView = (ListView)findViewById(R.id.expenseDetailsList);

        lView.setAdapter(new ExpenseDetailsAdapter(this,list));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_expense_details, menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

    public void Back(View view)
    {
        Intent intent = new Intent(this, ViewExpenseActivity.class);
        this.finish();
        startActivity(intent);
        //Log.i("Btn Click : ", "go to expense summary");
    }

    public  void Export(View v)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        StringBuilder sb = new StringBuilder();
        String dataString;
        for(Expense e : list) {
            sb.append("\"" + e.getAmount() +"\",\"" + String.valueOf(dateFormat.format(e.getCreated())) + "\",\"" + e.getCategory() + "\",\"" + e.getComment() + "\"\n");
        }

        String columnString =   "\"Amount\",\"Date\",\"Category\",\"Comments\"";
        //String dataString   =   "\"" + currentUser.userName +"\",\"" + currentUser.gender + "\",\"" + currentUser.street1 + "\",\"" + currentUser.postOFfice.toString()+ "\",\"" + currentUser.age.toString() + "\"";
        String combinedString = columnString + "\n" + sb.toString();

        File file   = null;
        File root   = Environment.getExternalStorageDirectory();
        if (root.canWrite()){
            File dir    =   new File (root.getAbsolutePath() + "/ExpenseData");
            dir.mkdirs();
            file   =   new File(dir, "ExpenseData.csv");
            FileOutputStream out   =   null;
            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(combinedString.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
                Utility.MsbBar(this,"ExpenseData\\ExpenseData.csv created on sdcard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Utility.MsbBar(this,"Error. Please confirm if sdcard inserted.");
        }
    }



    public void Delete(View v)
    {
        String id = String.valueOf(v.getTag());
        //Log.i("ID ", id);
        Expense result = realm.where(Expense.class)
                .equalTo("id", id).findFirst();
        //.findAll();
        if(result != null) {

            realm.beginTransaction();
            result.removeFromRealm();
            realm.commitTransaction();
            //Log.i("REMOVED","");


            Utility.MsbBar(this,"Record Deleted");

            Intent intent = getIntent();
            finish();
            startActivity(intent);

        }
    }

    public void Home(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(intent);
        //Log.i("Home Btn Click : ", "go to home");
    }
}
