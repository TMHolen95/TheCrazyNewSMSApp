package com.tmholen.thecrazynewsmsapp;

import android.net.Uri;
import android.support.annotation.WorkerThread;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dogsh on 11-Sep-16.
 */

public abstract class Tools {
    private SimpleDateFormat todayFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private SimpleDateFormat yesterdayFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private SimpleDateFormat daysAgoFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());

    public Uri ParseResourceToUri(int resource){
        return Uri.parse("android.resource://com.tmholen.thecrazynewsmsapp/" + resource);
    }

    public String getRelevantStringDate(Calendar timestamp){
        String result;
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE,-1);

        if(today.get(Calendar.MONTH) == timestamp.get(Calendar.MONTH)
                && today.get(Calendar.YEAR) == timestamp.get(Calendar.YEAR)){
            if(today.get(Calendar.DAY_OF_MONTH) == timestamp.get(Calendar.DAY_OF_MONTH)){
                result = "Today at: " + todayFormat.format(timestamp.getTime());
            }else if(yesterday.get(Calendar.DAY_OF_MONTH) == timestamp.get(Calendar.DAY_OF_MONTH)){
                result ="Yesterday at: " +  yesterdayFormat.format(timestamp.getTime());
            }else{
                result = daysAgoFormat.format(timestamp.getTime());
            }
        }else{
            result = daysAgoFormat.format(timestamp.getTime());
        }

        return result;
    }
}
