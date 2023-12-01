package com.zohaib.powertracker.utils.comparators;

import android.app.usage.UsageStats;

import java.util.Comparator;

public class UsageTimeComparator implements Comparator<UsageStats> {
    @Override
    public final int compare(UsageStats a, UsageStats b) {
        return (int)(b.getTotalTimeInForeground() - a.getTotalTimeInForeground());
    }
}