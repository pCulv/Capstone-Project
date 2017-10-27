package com.annoyedandroid.ancora.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.model.Timer;
import com.annoyedandroid.ancora.ui.fragments.NewTimerFragment;

import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.RecyclerViewHolders> {

    private List<Timer> mTimers;
    private Context mContext;
    private int TIMER_HOUR;
    private int TIMER_MIN;
    private int TIMER_SEC;

    public TimerAdapter(List<Timer> mTimers, Context mContext) {
        this.mTimers = mTimers;
        this.mContext = mContext;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timer_list_item, parent, false);
        RecyclerViewHolders viewHolder;
        viewHolder = new RecyclerViewHolders(layoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, int position) {
        holder.timerName.setText(mTimers.get(position).getTimerName());
        //todo: load chronometer that has been started after timer creation
        // start timer countdown

        // This is filled in with dummy data
        Bundle bundle = new Bundle();





    }

    public void bundle(Bundle bundle) {

        long getHour = bundle.getInt(NewTimerFragment.HOUR, TIMER_HOUR);
        long getMin = bundle.getInt(NewTimerFragment.MIN, TIMER_MIN);
        long getSec = bundle.getInt(NewTimerFragment.SEC, TIMER_SEC) ;

    }
    @Override
    public int getItemCount() {
        return this.mTimers.size();
    }

    class RecyclerViewHolders extends RecyclerView.ViewHolder {

        TextView timerName;
        Chronometer timerChronometer;

        RecyclerViewHolders(View itemView) {
            super(itemView);

            timerName = itemView.findViewById(R.id.timerName);
            timerChronometer = itemView.findViewById(R.id.chronometer2);

        }

    }
}
