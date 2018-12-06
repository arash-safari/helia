package com.safari.arash.helia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.safari.arash.helia.database.ActivityLog;
import com.safari.arash.helia.database.Goal;

import io.objectbox.Box;

public class InputGoalActivity extends AppCompatActivity {
    AppCompatButton submit;
    AppCompatEditText inputGoal;
    private Box<Goal> GoalBox;
    private Goal goal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_goal);
        submit = findViewById(R.id.submit);
        inputGoal = findViewById(R.id.inputGoal);
        GoalBox = (((App) getApplication()).getBoxStore()).boxFor(Goal.class);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitClicked();
            }
        });
    }


    private void submitClicked() {
        goal = new Goal();
        goal.name = inputGoal.getText().toString();
        GoalBox.put(goal);
        Intent intent = new Intent(this,TrackGoalActivity.class);
        startActivity(intent);
    }
}
