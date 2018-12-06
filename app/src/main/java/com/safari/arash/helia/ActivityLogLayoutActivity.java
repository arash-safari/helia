package com.safari.arash.helia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityLogLayoutActivity extends AppCompatActivity {
    List<String> durations,activities;
    AppCompatSpinner selectDuration,selectActivity;
    AppCompatButton next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_layout);
        initialDuration();
        initialActivity();
        selectDuration = findViewById(R.id.select_duration);
        selectActivity = findViewById(R.id.select_activity);
        next = findViewById(R.id.next);
        ArrayAdapter<String> durationAdapter =
                new ArrayAdapter<String>
                        (this, android.R.layout.simple_spinner_item, durations);
        ArrayAdapter<String> activityAdapter =
                new ArrayAdapter<String>
                        (this, android.R.layout.simple_spinner_item, activities);
        durationAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        activityAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        selectDuration.setAdapter(durationAdapter);
        selectActivity.setAdapter(activityAdapter);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClicked();
            }
        });
    }

    private void nextClicked() {
        Intent intent = new Intent(this,ActivityLogLayout2Activity.class);
        intent.putExtra("duration",
                durations.get(selectDuration.getSelectedItemPosition()));
        intent.putExtra("activity",
                activities.get(selectActivity.getSelectedItemPosition()));
        startActivity(intent);
    }

    private void initialActivity() {
        activities = new ArrayList<String>();
        activities.add("Normal Walking");
        activities.add("stretching");
        activities.add("Aerobic exercise");
        activities.add("Balance exercise");
        activities.add("Strength training");
    }

    private void initialDuration() {
        durations = new ArrayList<String>();
        durations.add("Less than 30 min");
        durations.add("30 min");
        durations.add("1 hour");
        durations.add("1 hour 30 min");
        durations.add("2 hour");
        durations.add("More than 2 hours");
    }
}
