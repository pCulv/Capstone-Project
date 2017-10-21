package com.annoyedandroid.ancora.ui.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.model.Timer;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.RecyclerViewHolders> {

    private List<Timer> mTimers;
    private Context mContext;


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


        long timerHour = TimeUnit.HOURS.toMillis(2);
        long timerMin = TimeUnit.MINUTES.toMillis(45);
        long timerSec = TimeUnit.SECONDS.toMillis(14);

        long totalTime = timerHour + timerMin + timerSec;

        new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                holder.timerChronometer.setText((millisUntilFinished / 3600000) + ":" +
                        (millisUntilFinished % 3600000 / 60000) + ":"+(millisUntilFinished % 60000 / 1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(mContext, "Timer Finished", Toast.LENGTH_SHORT).show();
            }
        }.start();
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
