package com.zohaib.powertracker.utils.comparators;

import android.app.usage.UsageStats;

import java.util.Comparator;
import java.util.Map;

public class AppNameComparator implements Comparator<UsageStats> {
    private Map<String, String> mAppLabelList;

    public AppNameComparator(Map<String, String> appList) {
        mAppLabelList = appList;
    }

    @Override
    public final int compare(UsageStats a, UsageStats b) {
        String alabel = mAppLabelList.get(a.getPackageName());
        String blabel = mAppLabelList.get(b.getPackageName());
        assert blabel != null;
        assert alabel != null;
        return alabel.compareTo(blabel);
    }
}