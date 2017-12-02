package com.fernandocejas.sample.threading

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.fernandocejas.sample.threading.threads.ThreadsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnThreads.setOnClickListener { navigateTo(ThreadsActivity::class.java) }
    }

    private fun navigateTo(activity: Class<ThreadsActivity>) {
        startActivity(Intent(this, activity))
    }
}
