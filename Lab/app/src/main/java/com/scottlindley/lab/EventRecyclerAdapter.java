package com.scottlindley.lab;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Scott Lindley on 11/28/2016.
 */

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder>{
    private List<Event> mEvents;
    private static final String TAG = "EventRecyclerAdapter";

    public EventRecyclerAdapter(List<Event> events) {
        mEvents = events;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, int position) {
        holder.mEventName.setText(mEvents.get(position).getName());
        holder.mEventDate.setText(mEvents.get(position).getDate());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                long id = mEvents.get(holder.getAdapterPosition()).getId();
                Log.d(TAG, "onLongClick: "+id);
                Uri uriWithId = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);
                int rows = view.getContext().getContentResolver().delete(
                        uriWithId,
                        null, null);
                Log.d(TAG, "onLongClick: "+rows);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public void swapData(Cursor cursor){
        Log.d(TAG, "swapData: ");
        mEvents.clear();

        if(cursor != null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String name = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
                String date = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
                long id = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events._ID));
                mEvents.add(new Event(name, date, id));
                cursor.moveToNext();
            }
        }

        notifyDataSetChanged();
    }


    public class EventViewHolder extends RecyclerView.ViewHolder{
        public TextView mEventName, mEventDate;

        public EventViewHolder(View itemView) {
            super(itemView);
            mEventName = (TextView)itemView.findViewById(R.id.event_name);
            mEventDate = (TextView)itemView.findViewById(R.id.event_date);
        }
    }
}
