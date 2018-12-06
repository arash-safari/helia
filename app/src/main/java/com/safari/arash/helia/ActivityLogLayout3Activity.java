package com.safari.arash.helia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.safari.arash.helia.database.ActivityLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.objectbox.Box;

public class ActivityLogLayout3Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    AppCompatButton submit;
    private static final String TAG = "ActivityLogLayout3Activ";
    private Box<ActivityLog> ActivityLogBox;
    String[] text = {"NO Hurt",
            "Hurts Little Bit",
            "Hurts Little More",
            "Hurt Even More",
            "Hurt Whole Lot",
            "Hurt Worst"};
    int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_layout3);
        PainAdapter painAdapter = new PainAdapter();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(painAdapter);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitClicked();
            }
        });
    }

    private void submitClicked() {
        Bundle bundle = getIntent().
                getBundleExtra("bundle");
        ActivityLog activityLog = new ActivityLog();
        activityLog.duration = bundle.getString("duration");
        activityLog.activity = bundle.getString("activity");
        activityLog.sleepDuration = bundle.getString("sleepDuration");
        activityLog.sleepQuality = bundle.getString("sleepQuality");
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        activityLog.date =  sf.format(Calendar.getInstance().getTime());
        Log.i(TAG, "submitClicked: " +activityLog.date);
//        Log.i(TAG, "submitClicked: " +activityLog.date.getMonth());
//        Log.i(TAG, "submitClicked: " +activityLog.date.getDay());
        activityLog.intensity = selected*2;
        ActivityLogBox = (((App) getApplication()).getBoxStore()).boxFor(ActivityLog.class);
        ActivityLogBox.put(activityLog);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    class PainAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.item_pain, viewGroup, false);
            return new PainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            PainViewHolder painViewHolder = (PainViewHolder) viewHolder;
            painViewHolder.text.setText(text[i]);
            painViewHolder.number.setText(i * 2 + "");
            switch (i) {
                case 0:
                    painViewHolder.image.setImageDrawable
                            (getResources().getDrawable(R.drawable.f1));
                    break;
                case 1:
                    painViewHolder.image.setImageDrawable
                            (getResources().getDrawable(R.drawable.f2));
                    break;
                case 2:
                    painViewHolder.image.setImageDrawable
                            (getResources().getDrawable(R.drawable.f3));
                    break;
                case 3:
                    painViewHolder.image.setImageDrawable
                            (getResources().getDrawable(R.drawable.f4));
                    break;
                case 4:
                    painViewHolder.image.setImageDrawable
                            (getResources().getDrawable(R.drawable.f5));
                    break;
                default:
                    painViewHolder.image.setImageDrawable
                            (getResources().getDrawable(R.drawable.f6));
            }
            final int index =i;
            painViewHolder.linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selected==index)
                        selected=-1;
                    else
                        selected = index;

                    PainAdapter.this.notifyDataSetChanged();
                }
            });
            if(selected==i){
                painViewHolder.linear.setBackgroundColor(
                        getResources().getColor(R.color.colorPrimary));
            }else{
                painViewHolder.linear.setBackgroundColor(
                        getResources().getColor(R.color.white));
            }
        }

        @Override
        public int getItemCount() {
            return 6;
        }

        private class PainViewHolder extends RecyclerView.ViewHolder {
            AppCompatImageView image;
            AppCompatTextView number, text;
            LinearLayout linear;
            public PainViewHolder(View view) {
                super(view);
                image = view.findViewById(R.id.image);
                number = view.findViewById(R.id.number);
                text = view.findViewById(R.id.text);
                linear = view.findViewById(R.id.linear);
            }
        }
    }
}
