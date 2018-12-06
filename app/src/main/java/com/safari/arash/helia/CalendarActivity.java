package com.safari.arash.helia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    CalendarView calendarView;
    AppCompatButton submit;
    String date;
    SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = findViewById(R.id.calendar);
        Calendar calendar = Calendar.getInstance();
        date = sf.format(calendar.getTime());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int y, int m, int d) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(y,m,d);
                date = sf.format(calendar.getTime());
            }
        });
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this
                        ,ShowActivityLogActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });

    }
}
