package com.safari.arash.helia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.safari.arash.helia.utils.SharedPreferenceUtils;

public class ModuleActivity extends AppCompatActivity {
    private static final String TAG = "ModuleActivity";
    AppCompatImageButton video,quiz,activity;
    AppCompatButton nextModule;
    AppCompatTextView title;
    ProgressBar progress;
    int moduleNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        title= findViewById(R.id.title);
        video= findViewById(R.id.video);
        quiz= findViewById(R.id.quiz);
        activity= findViewById(R.id.activity);
        nextModule= findViewById(R.id.next_module);
        progress= findViewById(R.id.progress);
        Intent intent = getIntent();
        moduleNumber = intent.getIntExtra("module",1);
        title.setText("Module " + (moduleNumber+1));
        nextModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextModuleClicked(moduleNumber);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClicked(moduleNumber+1);
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizClicked(moduleNumber+1);
            }
        });
    }

    private void quizClicked(int i) {
        Intent intent = new Intent(this,QuizActivity.class);
        intent.putExtra("number",i);
        startActivity(intent);
    }

    private void videoClicked(int i) {
        Intent intent = new Intent(this,VideoActivity.class);
        intent.putExtra("number",i);
        startActivity(intent);
    }

    private void nextModuleClicked(int moduleNumber) {
        SharedPreferenceUtils sharedPreferenceUtils =
                SharedPreferenceUtils.getInstance(this);
        int moduleSize = sharedPreferenceUtils.getModulesSize();
        int nextModuleNumber = moduleNumber+1;
        if(nextModuleNumber<moduleSize){
            boolean nextModuleStatus = sharedPreferenceUtils
                    .getModulesStatus(nextModuleNumber);
            if(nextModuleStatus){
                goToNextModule(nextModuleNumber);
            }
        }
    }

    private void goToNextModule(int nextModuleNumber) {
        Intent intent = new Intent(this,ModuleActivity.class);
        intent.putExtra("module",nextModuleNumber);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferenceUtils sharedPreferenceUtils =
                SharedPreferenceUtils.getInstance(this);
        int p = sharedPreferenceUtils.getPrecentageModule(moduleNumber);
        Log.i(TAG, "onStart: precentage " + p);
        progress.setProgress(p);
    }
}
