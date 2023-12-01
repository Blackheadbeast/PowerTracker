package com.zohaib.powertracker

import android.app.AppOpsManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.zohaib.powertracker.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setupUI()
    }

    private fun ActivitySplashBinding.setupUI() {
        //Request Permission from User
        val appOps = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), packageName
        )
        if (mode == AppOpsManager.MODE_ALLOWED) {
            splashWelcomeText.text = "Already have Permissions. Good to go"
            setupTimer()
        } else {
            splashWelcomeText.text = "Please allow Usage Permission in Settings"
            startActivityForResult(
                Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS
            )
        }
    }

    private fun setupTimer() {
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, AppUsageActivity::class.java))
            finish()
        }, 500)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS) {
            binding.splashWelcomeText.text = "Usage Permission Granted"
            setupTimer()
        } else {
            binding.splashWelcomeText.text = "Please grant Permission to continue"
        }
    }
}