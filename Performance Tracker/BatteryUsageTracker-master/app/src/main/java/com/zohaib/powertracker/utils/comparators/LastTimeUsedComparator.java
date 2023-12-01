package com.zohaib.powertracker.utils.comparators;

import android.app.usage.UsageStats;

import java.util.Comparator;

public class LastTimeUsedComparator implements Comparator<UsageStats> {
    @Override
    public final int compare(UsageStats a, UsageStats b) {
        // return by descending order
        return (int)(b.getLastTimeUsed() - a.getLastTimeUsed());
    }
}