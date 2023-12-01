package com.zohaib.powertracker.fragments

import android.annotation.SuppressLint
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.zohaib.powertracker.TrackingDetailsActivity
import com.zohaib.powertracker.databinding.FragmentTimeUsageBinding
import com.zohaib.powertracker.utils.CalendarUtils


class TimeUsageFragment : Fragment() {
    private val TAG = "##@@@TimeUsageFrag"

    private var _binding: FragmentTimeUsageBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUsageStatsManager: UsageStatsManager
    private lateinit var mPm: PackageManager
    private lateinit var appInfo: ApplicationInfo
    private var pkgName = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimeUsageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        binding.setupUI()
    }

    private fun setup() {
        mPm = requireActivity().packageManager
        pkgName = TrackingDetailsActivity.pkgName
        appInfo = mPm.getApplicationInfo(TrackingDetailsActivity.pkgName, 0)
        mUsageStatsManager =
            (requireContext().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager?)!!
        binding.updateUI("Past Week")
    }

    private fun FragmentTimeUsageBinding.setupUI() {
        //setupSpinner(binding) todo: Setup Sorting spinner also

        //For filters:------
        binding.filtersRadioGroup.apply {
            setOnCheckedChangeListener { group, checkedId ->
                val selectedFilter = view?.findViewById<RadioButton>(checkedId)?.text.toString()
                updateUI(selectedFilter)
            }
        }

        //Set App Icon in ImageView
        try {
            val appInfo: ApplicationInfo = mPm.getApplicationInfo(pkgName, 0)
            val appIcon = appInfo.loadIcon(mPm)
            appIconImageView.setImageDrawable(appIcon)
        } catch (exception: Exception) {
            Log.w(TAG, "setupUI(): cannot set App Icon image into IV")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun FragmentTimeUsageBinding.updateUI(selectedFilter: String) {
        val startTime = CalendarUtils.getCustomAdjustedSystemTimeMillis(selectedFilter)
        val endTime = System.currentTimeMillis()

        val usageStatsList =
            mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime)
        for (usageStats in usageStatsList) {
            if (usageStats.packageName == pkgName) {
                // Process or display the relevant usage information
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    totalTimeVisible.text =
                        "Total Time Visible: ${CalendarUtils.formatDuration(usageStats.totalTimeVisible)}"
                    lastTimeVisible.text =
                        "Last Time Visible: ${CalendarUtils.formatDuration(usageStats.lastTimeVisible)}"
                    totalTimeForegroundServiceUsed.text =
                        "Total Time in Foreground Service Used: ${
                            CalendarUtils.formatDuration(
                                usageStats.totalTimeForegroundServiceUsed
                            )
                        }"
                    lastTimeForegroundServiceUsed.text =
                        "Last Time in Foreground Service Used: ${
                            CalendarUtils.formatDuration(
                                usageStats.lastTimeForegroundServiceUsed
                            )
                        }"
                }
                lastTimeUsed.text =
                    "lastTimeUsed: ${CalendarUtils.formatDuration(usageStats.lastTimeUsed)}"
                firstTimeStamp.text =
                    "First Time Stamp: ${CalendarUtils.formatDuration(usageStats.firstTimeStamp)}"
                lastTimeStamp.text =
                    "First Time Stamp: ${CalendarUtils.formatDuration(usageStats.lastTimeStamp)}"
                packageName.text = "Package Name: $pkgName"
                totalTimeInForeground.text =
                    "Total Time in Foreground: ${CalendarUtils.formatDuration(usageStats.totalTimeInForeground)}"
                break // Break the loop once the desired package is found
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}