package com.zohaib.powertracker

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.zohaib.powertracker.adapters.ViewPagerTrackingDetailsAdapter
import com.zohaib.powertracker.databinding.ActivityTrackingDetailsBinding
import com.zohaib.powertracker.utils.FieldConstants

class TrackingDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrackingDetailsBinding
    private lateinit var mPm: PackageManager
    private lateinit var appInfo: ApplicationInfo

    private var appName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
        binding.setupUI()
    }

    private fun setup() {
        mPm = packageManager
        pkgName = intent.getStringExtra(FieldConstants.APP_PKG_NAME) ?: ""
        appInfo = mPm.getApplicationInfo(pkgName, 0)
        appName = appInfo.loadLabel(mPm).toString()
    }

    private fun ActivityTrackingDetailsBinding.setupUI() {
        viewpager.adapter = ViewPagerTrackingDetailsAdapter(this@TrackingDetailsActivity)
        TabLayoutMediator(binding.slidingTabs, binding.viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> "Time"
                else -> ""
            }
        }.attach()

        appBarTitle.text = appName

    }

    companion object {
        var pkgName = ""
            private set
    }
}