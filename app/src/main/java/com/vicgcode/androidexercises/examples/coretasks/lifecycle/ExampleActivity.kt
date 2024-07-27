package com.vicgcode.androidexercises.examples.coretasks.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ExampleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // MUST BE implemented
        // CREATED
    }

    override fun onStart() {
        super.onStart()
        // VISIBLE
    }

    override fun onResume() {
        super.onResume()
        // ACTIVE
        // User returns from onPause()
    }

    override fun onRestart() {
        super.onRestart()
        // RESTARTING
        // User navigates to this activity, f.e. click btn back
        // next onStart()
    }

    override fun onPause() {
        super.onPause()
        // PAUSED
        // HALF VISIBLE
        // next onResume() or onStop()
        // Another activity comes into the foreground
    }

    override fun onStop() {
        super.onStop()
        // NOT VISIBLE
        // STOPPED
        // next onRestart() or onDestroy() or onCreate()
        // onCreate() if system killed this activity because memory
        // when activity is in backstack
    }

    override fun onDestroy() {
        super.onDestroy()
        // DESTROYED
    }
}
