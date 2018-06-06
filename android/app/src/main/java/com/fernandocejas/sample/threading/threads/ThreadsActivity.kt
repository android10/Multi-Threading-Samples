package com.fernandocejas.sample.threading.threads

import android.app.Activity
import android.os.Bundle
import com.fernandocejas.sample.threading.R
import com.fernandocejas.sample.threading.data.Source
import kotlinx.android.synthetic.main.activity_threads.btnRun

class ThreadsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)
        btnRun.setOnClickListener { rxJavaParallelStreamWordCount() }
    }

    //Threads and Locks Examples
    private fun threadsSequentialWordCount() {
        com.fernandocejas.sample.threading.threads.SequentialWordCount().run()
    }

    private fun threadsTwoThreadsWordCount() {
        com.fernandocejas.sample.threading.threads.TwoThreadsWordCount().run()
    }
    //---------------------------



    //RxJava 2 Examples
    private fun rxJavaSequentialWordCount() {
        com.fernandocejas.sample.threading.rxjava.SequentialWordCount().run()
    }

    private fun rxJavaTwoThreadsWordCount() {
        com.fernandocejas.sample.threading.rxjava.TwoThreadsWordCount().run()
    }

    private fun rxJavaParallelStreamWordCount() {
        com.fernandocejas.sample.threading.rxjava.ParallelStreamWordCount().run()
    }
    //---------------------------



    //Kotlin Coroutines Examples
    private fun coroutinesSequentialWordCount() {
        com.fernandocejas.sample.threading.coroutines.SequentialWordCount().run()
    }

    private fun coroutinesTwoThreadsWordCount() {
        com.fernandocejas.sample.threading.coroutines.TwoThreadsWordCount().run()
    }

    private fun coroutinesBetterWordCount() {
        com.fernandocejas.sample.threading.coroutines.BetterWordCount(Source()).run()
    }
    //---------------------------
}