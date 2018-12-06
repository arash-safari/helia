package com.safari.arash.helia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.safari.arash.helia.utils.SharedPreferenceUtils;

public class QuizActivity extends AppCompatActivity {
    AppCompatTextView question,option1,option2;
    String[] questions = {"Which one is correct for Recalibrating your nociceptors and peripheral nerves? ",
            "Catastrophizing is …",
            "stress is …",
            "The three curves are in correct alignment when the ears, shoulders, hips, knees, and ankles are in a straight line.",
            "Progressive muscle relaxation, or PMR, ",
            "Guided imagery is a relaxation technique that uses the power of your imagination to bring about ",
            "It reduces inflammation, and numbs the affected area which is usually present in any type of back pain.",
    };
    String[] options1 = {
            "1.\tUsing ice, heat or electrical stimuli",
            "1.\ta way of responding to pain that can impair you from reaching your goal",
            "1.\tthe pressure from work, education and etc. ",
            "1.\tTrue",
            "1.\tis an exercise that stretches all of your muscles",
            "1.\tdesired physical changes.",
            "1.\tIt is an application for ice ",
    };
    String[] options2 = {
            "2.\tExercising heavily ",
            "2.\ta way to release your pain by medicine",
            "2.\t your body’s natural response to change in your life",
            "2.\tFalse",
            "2.\tis an exercise that relaxes your mind and body by progressively tensing and relaxing muscle groups throughout your entire body.",
            "2.\tMental activities",
            "2.\tIt is an application for heat",
    };
    int[] answer ={0,0,1,0,1,0,1};
    int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        number = getIntent().getIntExtra("number",1);
        question.setText(questions[number-1]);
        option1.setText(options1[number-1]);
        option2.setText(options2[number-1]);
        int rightAnswerColor = R.color.green;
        int wrongAnswerColor = R.color.red;
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionClicked(0);
                int option1Color = answer[number-1]==0?rightAnswerColor:wrongAnswerColor;
                int option2Color = answer[number-1]==0?wrongAnswerColor:rightAnswerColor;
                option1.setBackgroundColor(getResources().getColor(option1Color));
                option2.setBackgroundColor(getResources().getColor(option2Color));
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionClicked(1);
                int option1Color = answer[number-1]==0?rightAnswerColor:wrongAnswerColor;
                int option2Color = answer[number-1]==0?wrongAnswerColor:rightAnswerColor;
                option1.setBackgroundColor(getResources().getColor(option1Color));
                option2.setBackgroundColor(getResources().getColor(option2Color));
            }
        });
    }

    private void optionClicked(int i) {
        SharedPreferenceUtils sharedPreferenceUtils =
                SharedPreferenceUtils.getInstance(this);
        if(i==answer[number-1]){
            sharedPreferenceUtils.putPrecentageModule(number-1,100);
            if(number<sharedPreferenceUtils.getModulesSize()&&
                    !sharedPreferenceUtils.getModulesStatus(number))
            sharedPreferenceUtils.putModulesStatus(number,true);
        }
    }
}
