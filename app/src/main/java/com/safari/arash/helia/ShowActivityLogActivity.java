package com.safari.arash.helia;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safari.arash.helia.database.ActivityLog;
import com.safari.arash.helia.database.ActivityLog_;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.objectbox.Box;

public class ShowActivityLogActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    private static final String TAG = "ShowActivityLogActivity";
    ShowActivityAdapter showActivityAdapter;
    private Box<ActivityLog> ActivityLogBox;
    private List<ActivityLog> ActivityLogBoxOnDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);
        showActivityAdapter = new ShowActivityAdapter();

        String date = getIntent().getStringExtra("date");
        ActivityLogBox = (((App) getApplication()).getBoxStore()).boxFor(ActivityLog.class);
        ActivityLogBoxOnDate = ActivityLogBox.query()
                .equal(ActivityLog_.date, date).build().find();
        Log.i(TAG, "onCreate: " + date);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(showActivityAdapter);
    }
    class ShowActivityAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.item_show_log, viewGroup, false);
            return new ShowActivityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ShowActivityViewHolder sHolder = (ShowActivityViewHolder) viewHolder;
            sHolder.activity.setText(ActivityLogBoxOnDate.get(i).activity);
            sHolder.duration.setText(ActivityLogBoxOnDate.get(i).duration);
            sHolder.sleepDuration.setText(ActivityLogBoxOnDate.get(i).sleepDuration);
            sHolder.sleepQuality.setText(ActivityLogBoxOnDate.get(i).sleepQuality);
            sHolder.intesity.setText(ActivityLogBoxOnDate.get(i).intensity+"");
        }

        @Override
        public int getItemCount() {
            return ActivityLogBoxOnDate.size();
        }
        private class ShowActivityViewHolder extends RecyclerView.ViewHolder {
            AppCompatTextView duration,activity,
                    sleepDuration,sleepQuality,intesity;
            public ShowActivityViewHolder(View view) {
                super(view);
                duration = view.findViewById(R.id.duration);
                activity = view.findViewById(R.id.activity);
                sleepDuration = view.findViewById(R.id.sleep_duration);
                sleepQuality = view.findViewById(R.id.sleep_quality);
                intesity = view.findViewById(R.id.pain_intesity);
            }
        }
    }
}
