package com.scottlindley.lab;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Scott Lindley on 11/28/2016.
 */

public class Event{
    private String mName, mDate;
    private long mId;

    public Event(String name, String date, long id) {
        mName = name;
        setDate(date);
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public String getDate() {
        return mDate;
    }

    public long getId() {
        return mId;
    }

    public void setDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        mDate = formatter.format(new Date(Long.valueOf(date)));
    }
}
