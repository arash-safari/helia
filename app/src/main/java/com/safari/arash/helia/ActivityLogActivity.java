package com.safari.arash.helia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

public class ActivityLogActivity extends AppCompatActivity {
    AppCompatImageButton activity,calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        activity = findViewById(R.id.activity);
        calendar = findViewById(R.id.calendar);
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityClicked();
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarClicked();
            }
        });
    }

    private void calendarClicked() {
        Intent intent = new Intent(this,CalendarActivity.class);
        startActivity(intent);
    }

    private void activityClicked() {
        Intent intent = new Intent(this,ActivityLogLayoutActivity.class);
        startActivity(intent);
    }
}
