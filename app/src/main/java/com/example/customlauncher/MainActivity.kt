package com.example.customlauncher

import android.content.Intent
import android.net.sip.SipErrorCode.TIME_OUT
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.customlauncher.appsdrawer.AppsDrawerActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed(Runnable {
            val i = Intent(this@MainActivity, AppsDrawerActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }, 6000)
//        startActivity(Intent(this, AppsDrawerActivity::class.java))
    }
}