package com.annoyedandroid.ancora.ui.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annoyedandroid.ancora.R;
import com.annoyedandroid.ancora.model.TimerModel;

import java.util.List;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.RecyclerViewHolders> {

    private List<TimerModel> mTimers;
    private Context mContext;


    public TimerAdapter(List<TimerModel> timers, Context context) {
        this.mTimers = timers;
        this.mContext = context;
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
        //todo: load chronometer that has been started after timer creation;

        if (holder.timer != null) {
            holder.timer.cancel();
        }

        long timer = (mTimers.get(position).getTotalTime());


            holder.timer = new CountDownTimer(timer, 1000) {
                public void onTick(long millisUntilFinished) {
                    holder.countdownTxt.setText("" + millisUntilFinished / 3600000 + ":" +
                            millisUntilFinished % 3600000 / 60000 + ":" + millisUntilFinished % 60000 / 1000);
                }

                public void onFinish() {
                    holder.countdownTxt.setText("0:00:00");
                }
            }.start();


    }

    @Override
    public int getItemCount() {
        return this.mTimers.size();
    }

    class RecyclerViewHolders extends RecyclerView.ViewHolder {

        TextView timerName;
        TextView countdownTxt;
        CountDownTimer timer;

        RecyclerViewHolders(View itemView) {
            super(itemView);

            timerName = itemView.findViewById(R.id.timerName);
            countdownTxt = itemView.findViewById(R.id.countdownTextView);

        }

    }
}
