package com.fernandocejas.sample.threading.threads

import android.app.Activity
import android.os.Bundle
import com.fernandocejas.sample.threading.R
import kotlinx.android.synthetic.main.activity_threads.*

class ThreadsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        btnRun.setOnClickListener { WordCount().runSequentially() }
    }
}