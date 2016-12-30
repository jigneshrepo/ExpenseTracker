package com.androidapps.jignesh.expensetracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidapps.jignesh.expensetracker.model.Expense;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by developer on 4/8/2015.
 * http://www.myandroidsolutions.com/2012/07/19/android-listview-with-viewholder-tutorial/
 */
public class ExpenseDetailsAdapter extends BaseAdapter {
    private ArrayList<Expense> mListItems;
    private LayoutInflater mLayoutInflater;

    public  ExpenseDetailsAdapter(Context context, ArrayList<Expense> arrayList)
    {
        mListItems = arrayList;

        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // create a ViewHolder reference
        ViewHolder holder;

        //check to see if the reused view is null or not, if is not null then reuse it
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = mLayoutInflater.inflate(R.layout.expense_details_item, null);
            holder.tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvComments = (TextView) convertView.findViewById(R.id.tvComments);
            holder.tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
            holder.btnDelete = (ImageButton) convertView.findViewById(R.id.delBtn);
            // the setTag is used to store the data within this view
            convertView.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        //get the string item from the position "position" from array list to put it on the TextView
        Expense exp = mListItems.get(position);
        if (exp != null) {
            if (holder.tvAmount != null) {
                //set the item name on the TextView
                holder.tvAmount.setText(String.valueOf(exp.getAmount()));
                holder.tvDate.setText(Utility.GetFormattedDate(exp.getCreated()));
                holder.tvComments.setText("Comments : " + exp.getComment());
                holder.tvCategory.setText("Category : " + exp.getCategory());
                holder.btnDelete.setTag(exp.getId());

            }
        }
        //exp = null;
        //this method must return the view corresponding to the data at the specified position.
        return convertView;

    }

    static class ViewHolder{
        TextView tvAmount;
        TextView tvDate;
        TextView tvComments;
        TextView tvCategory;
        ImageButton btnDelete;
    }



}


