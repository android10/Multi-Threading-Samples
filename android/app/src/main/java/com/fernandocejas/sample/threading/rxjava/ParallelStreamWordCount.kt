package com.fernandocejas.sample.threading.rxjava

import android.util.Log
import com.fernandocejas.sample.threading.data.Pages
import com.fernandocejas.sample.threading.data.Source
import com.fernandocejas.sample.threading.data.Words
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.stream.Collectors
import java.util.stream.StreamSupport

class ParallelStreamWordCount {
    private val LOG_TAG = ParallelStreamWordCount::class.java.canonicalName

    fun run() {
        val startTime = System.currentTimeMillis()

        val singleLettersCount = Single.fromCallable {
            Pages(0, 700, Source().wikiPagesBatchOne())
                    .plus(Pages(0, 700, Source().wikiPagesBatchTwo()))
                    .parallelStream()
                    .flatMap { StreamSupport.stream(Words(it.text).spliterator(), false) }
                    .collect(Collectors.groupingBy({ it.toString() }, Collectors.counting()))
        }

        singleLettersCount
                .subscribeOn(Schedulers.io())
                .subscribe { data ->
                    logData(System.currentTimeMillis() - startTime, data.size)
                }
    }

    private fun logData(time: Long, count: Int) {
        Log.d(LOG_TAG, "Number of elements: $count")
        Log.d(LOG_TAG, "Execution Time: $time ms")
    }
}