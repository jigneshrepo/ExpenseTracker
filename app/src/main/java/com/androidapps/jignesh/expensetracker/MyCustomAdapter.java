package com.androidapps.jignesh.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 4/6/2015.
 * http://stackoverflow.com/questions/17525886/listview-with-add-and-delete-buttons-in-each-row-in-android
 */
public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<SummaryListItem> list = new ArrayList<SummaryListItem>();
    private Context context;



    public MyCustomAdapter(ArrayList<SummaryListItem> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;// list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_expense_summary, null);
        }

        //Handle TextView and display string from your list
        TextView tvTitle = (TextView)view.findViewById(R.id.tv_expense_for);
        tvTitle.setText(list.get(position).getTitle().name().replace('_',' '));

        TextView tvAmount = (TextView)view.findViewById(R.id.tv_amount);
        tvAmount.setText(String.valueOf(list.get(position).getAmount()));

        //Handle buttons and add onClickListeners
        /*com.beardedhen.androidbootstrap.FontAwesomeText txtBtnDetails = (com.beardedhen.androidbootstrap.FontAwesomeText)view.findViewById(R.id.txtBtnDetails);

        txtBtnDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Hello", Toast.LENGTH_LONG).show();
            }
        });*/


        return view;
    }
}