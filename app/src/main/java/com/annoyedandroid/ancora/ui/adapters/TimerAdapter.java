package com.annoyedandroid.ancora.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.model.Timer;

import java.util.ArrayList;
import java.util.List;


public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.TimerViewHolder> {

    private Context mContext;
    private List<Timer> mTimers = new ArrayList<>();

    public TimerAdapter(Context context, List<Timer> timers) {
        this.mContext = context;
        this.mTimers = timers;
    }

    @Override
    public TimerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        this.mContext = viewGroup.getContext();
        int layoutIdForTimers = R.layout.timer_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutIdForTimers, viewGroup, false);
        TimerViewHolder  timerViewHolder = new TimerViewHolder(view);

        return timerViewHolder;
    }

    @Override
    public void onBindViewHolder(TimerAdapter.TimerViewHolder holder, int position) {
        //Todo: retrieve timers from database

    }

    @Override
    public int getItemCount() {
        return mTimers.size();
    }

    public class TimerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView timerTitle;

        public TimerViewHolder(View itemView) {
            super(itemView);

            timerTitle = itemView.findViewById(R.id.timerName);
            timerTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Todo: build intents to open EditTimerActivity
        }
    }
}
