package com.safari.arash.helia.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Goal {
    @Id public long id;
    @NonNull public String name;
    @Nullable public String status;
}
