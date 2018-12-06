package com.safari.arash.helia;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.safari.arash.helia.utils.SharedPreferenceUtils;

public class ListModuleActivity extends AppCompatActivity {
    private ModulesAdapter modulesAdapter;
    RecyclerView recyclerView ;
    private static final String TAG = "ListModuleActivity";
    SharedPreferenceUtils sharedPreferencesUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_modules);
        sharedPreferencesUtils = SharedPreferenceUtils.getInstance(this);
        int modulesSize = sharedPreferencesUtils.getModulesSize();
        boolean[] moduleStatuses = new boolean[modulesSize];
        for (int i = 0; i < modulesSize; i++) {
            moduleStatuses[i] = sharedPreferencesUtils.getModulesStatus(i);
        }
        ModulesAdapter modulesAdapter = new ModulesAdapter();
        modulesAdapter.setStatuses(moduleStatuses);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(modulesAdapter);
    }

    class ModulesAdapter extends RecyclerView.Adapter {
        private boolean[] statuses;

        public void setStatuses(boolean[] statuses) {
            this.statuses = statuses;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.item_module, viewGroup, false);
            return new ModuleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ModuleViewHolder mHolder = (ModuleViewHolder) viewHolder;
            if (statuses[i])
                mHolder.lock.setImageResource(R.drawable.ic_play_arrow_white_24dp);
            else
                mHolder.lock.setImageResource(R.drawable.ic_lock_black_24dp);

            mHolder.title.setText("Module "+(i+1));
            final int index = i;
            mHolder.lock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moduleClicked(index);
                }
            });
        }

        @Override
        public int getItemCount() {
            return statuses.length;
        }

        private class ModuleViewHolder extends RecyclerView.ViewHolder {
            AppCompatImageButton lock;
            AppCompatTextView title;
            public ModuleViewHolder(View view) {
                super(view);
                lock = view.findViewById(R.id.lock);
                title = view.findViewById(R.id.title);
            }
        }
    }

    private void moduleClicked(int index) {
        boolean moduleStatus = sharedPreferencesUtils.getModulesStatus(index);
        if(moduleStatus){
            goToModule(index);
        }
    }

    private void goToModule(int index) {
        Intent intent = new Intent(this,ModuleActivity.class);
        intent.putExtra("module",index);
        startActivity(intent);
    }
}
