package com.safari.arash.helia;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoreInformationActivity extends AppCompatActivity {
    private MoreInfoAdapter moreInfoAdapter;
    RecyclerView recyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);
        moreInfoAdapter = new MoreInfoAdapter();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(moreInfoAdapter);
    }
    class MoreInfoAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.item_more_info, viewGroup, false);
            return new MoreInfoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 2;
        }
        private class MoreInfoViewHolder extends RecyclerView.ViewHolder {
            AppCompatTextView title,text;
            public MoreInfoViewHolder(View view) {
                super(view);
                text = view.findViewById(R.id.text);
                title = view.findViewById(R.id.title);
            }
        }
    }
}
