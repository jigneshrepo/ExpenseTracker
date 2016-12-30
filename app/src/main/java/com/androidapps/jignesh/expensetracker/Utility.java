package com.androidapps.jignesh.expensetracker;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by developer on 4/10/2015.
 */
public class Utility {
    public static void MsbBar(Context c, CharSequence message) {
        Toast.makeText(c, message, Toast.LENGTH_LONG).show();
    }

    public  static Date GetTodaysDate()
    {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);

        return c.getTime();
    }

    public static Date GetThisWeekFirstDate()
    {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);

        while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
        {
            c.add(Calendar.DATE, -1);
        }

        return  c.getTime();
    }

    public static Date GetThisMonthFirstDate()
    {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    public static Date GetThisYearFirstDate()
    {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.DAY_OF_YEAR, 1);// Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_YEAR));
        return c.getTime();
    }

    public static String GetFormattedDate(Date dt)
    {
        return new SimpleDateFormat("dd MMM yyyy").format(dt.getTime());
    }
}
