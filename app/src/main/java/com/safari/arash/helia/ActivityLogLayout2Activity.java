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

public class ActivityLogLayout2Activity extends AppCompatActivity {
    List<String> sleepDuration,sleepQualify;
    AppCompatSpinner selectSleepDuration,selectSleepQualify;
    AppCompatButton next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_layout2);
        initialSleepDuration();
        initialSleepQualify();
        selectSleepDuration = findViewById(R.id.select_sleep_duration);
        selectSleepQualify = findViewById(R.id.select_quilify_sleep);
        next = findViewById(R.id.next);
        ArrayAdapter<String> durationAdapter =
                new ArrayAdapter<String>
                        (this, android.R.layout.simple_spinner_item, sleepDuration);
        ArrayAdapter<String> qualifyAdapter =
                new ArrayAdapter<String>
                        (this, android.R.layout.simple_spinner_item, sleepQualify);
        durationAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        qualifyAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        selectSleepQualify.setAdapter(qualifyAdapter);
        selectSleepDuration.setAdapter(durationAdapter);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClicked();
            }
        });
    }

    private void nextClicked() {
        Intent intent = new Intent(this,ActivityLogLayout3Activity.class);
        Bundle bundle = getIntent().getExtras();
        bundle.putString("sleepQualify",
                sleepQualify.get(selectSleepQualify.getSelectedItemPosition()));
        bundle.putString("sleepDuration",
                sleepDuration.get(selectSleepDuration.getSelectedItemPosition()));
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    private void initialSleepQualify() {
        sleepQualify = new ArrayList<String>();
        sleepQualify.add("Very good");
        sleepQualify.add("Good");
        sleepQualify.add("Moderate");
        sleepQualify.add("Poor");
    }

    private void initialSleepDuration() {
        sleepDuration = new ArrayList<String>();
        sleepDuration.add("1 hr");
        sleepDuration.add("2 hr");
        sleepDuration.add("3 hr");
        sleepDuration.add("4 hr");
        sleepDuration.add("5 hr");
        sleepDuration.add("6 hr");
        sleepDuration.add("7 hr");
        sleepDuration.add("8 hr");
        sleepDuration.add("9 hr");
        sleepDuration.add("10 hr");
        sleepDuration.add("11 hr");
        sleepDuration.add("12 hr");
    }
}
