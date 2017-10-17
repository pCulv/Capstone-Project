package com.annoyedandroid.ancora.ui.adapters;

import android.content.Context;
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
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.timerName.setText(mTimers.get(position).getTimerName());
        //todo: load chronometer that has been started after timer creation

    }

    @Override
    public int getItemCount() {
        return this.mTimers.size();
    }

    class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView timerName;
        Chronometer timerChronometer;

        RecyclerViewHolders(View itemView) {
            super(itemView);

            timerName = itemView.findViewById(R.id.timerName);
            timerChronometer = itemView.findViewById(R.id.chronometer2);
        }

        @Override
        public void onClick(View v) {
            //todo: open editTimerActivity
            Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
