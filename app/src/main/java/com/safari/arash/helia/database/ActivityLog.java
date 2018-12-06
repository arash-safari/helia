package com.safari.arash.helia.database;

import android.support.annotation.NonNull;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ActivityLog {
    @Id public long id;
    @NonNull public String activity;
    @NonNull public String date;
    @NonNull public String duration;
    @NonNull public String sleepQuality;
    @NonNull public String sleepDuration;
    @NonNull public int intensity;
}
