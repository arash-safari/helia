package com.safari.arash.helia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.safari.arash.helia.database.ActivityLog_;
import com.safari.arash.helia.database.Goal;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class TrackGoalActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    private Box<Goal> GoalBox;
    private List<Goal> goalList;
    private GoalsAdapter goalsAdapter;
    AppCompatButton submit;
    List<String> goalStatuses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_goal);
        submit = findViewById(R.id.submit);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goalsAdapter = new GoalsAdapter();
        recyclerView.setAdapter(goalsAdapter);
        GoalBox = (((App) getApplication()).getBoxStore()).boxFor(Goal.class);
        goalList = GoalBox.query().build().find();
        goalsAdapter.notifyDataSetChanged();
        goalStatuses = new ArrayList<String>();
        goalStatuses.add("did not start yet");
        goalStatuses.add("in progress");
        goalStatuses.add("done");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitClicked();
            }
        });
    }

    private void submitClicked() {

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    class GoalsAdapter extends RecyclerView.Adapter{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.item_goal, viewGroup, false);
            return new GoalViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            GoalViewHolder gHolder = (GoalViewHolder) viewHolder;
            gHolder.name.setText(goalList.get(i).name);
            ArrayAdapter<String> statusAdapter =
                    new ArrayAdapter<String>
                            (getApplicationContext(),
                                    R.layout.spinner_item, goalStatuses);
            statusAdapter.setDropDownViewResource
                    (android.R.layout.simple_spinner_dropdown_item);
            gHolder.spinner.setAdapter(statusAdapter);
            gHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                 goalList.get(i).status = goalStatuses.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return goalList.size();
        }
        private class GoalViewHolder extends RecyclerView.ViewHolder {
            AppCompatTextView name;
            AppCompatSpinner spinner;
            public GoalViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.name);
                spinner = view.findViewById(R.id.spinner);
            }
        }

    }
}
