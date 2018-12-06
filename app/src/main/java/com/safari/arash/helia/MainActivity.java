package com.safari.arash.helia;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    AppCompatButton modules,activityLog,moreInformation,askANurse,goalSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modules = findViewById(R.id.modules);
        activityLog = findViewById(R.id.activity_log);
        moreInformation = findViewById(R.id.more_information);
        askANurse = findViewById(R.id.ask_a_nurse);
        goalSetting = findViewById(R.id.goal_setting);
        modules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(ListModuleActivity.class);
            }
        });
        activityLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(ActivityLogActivity.class);
            }
        });
        moreInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(MoreInformationActivity.class);
            }
        });
        askANurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(AskANurseActivity.class);
            }
        });
        goalSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(InputGoalActivity.class);
            }
        });
    }
    public void goToActivity(Class activityClass){
        Intent intent = new Intent(this,activityClass);
        startActivity(intent);
    }
}
