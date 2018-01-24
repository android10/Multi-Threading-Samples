package com.fernandocejas.sample.threading.rxjava

import android.util.Log
import com.fernandocejas.sample.threading.data.Pages
import com.fernandocejas.sample.threading.data.Source
import com.fernandocejas.sample.threading.data.Words
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlin.system.measureTimeMillis

class SequentialWordCount {
    private val LOG_TAG = SequentialWordCount::class.java.canonicalName

    private val counts: HashMap<String, Int?> = HashMap()

    fun run() {
        val startTime = System.currentTimeMillis()

        val observable = Observable.fromCallable {
            var pagesOne = Pages.empty()
            val pagesOneCreationTime = measureTimeMillis {
                pagesOne = Pages(0, 5000, Source().wikiPagesBatchOne())
            }
            pagesOne.forEach { page -> Words(page.text).forEach { countWord(it) } }

            var pagesTwo = Pages.empty()
            val pagesTwoCreationTime = measureTimeMillis {
                pagesTwo = Pages(0, 5000, Source().wikiPagesBatchTwo())
            }
            pagesTwo.forEach { page -> Words(page.text).forEach { countWord(it) } }

            logPagesCreationTime(pagesOneCreationTime, pagesTwoCreationTime)
        }

        observable
                .doOnComplete { logExecutionData(System.currentTimeMillis() - startTime) }
                .subscribeOn(Schedulers.single())
                .subscribe()
    }

    private fun countWord(word: String) {
        when(counts.containsKey(word)) {
            true -> counts[word] = counts[word]?.plus(1)
            false -> counts[word] = 1
        }
    }

    private fun logPagesCreationTime(timePagesOne: Long, timePagesTwo: Long) {
        Log.d(LOG_TAG, "PageOne creation time: $timePagesOne ms")
        Log.d(LOG_TAG, "PageOne creation time: $timePagesTwo ms")
        Log.d(LOG_TAG, "Total Execution Pages Creation: ${timePagesOne.plus(timePagesTwo)} ms")
    }

    private fun logExecutionData(time: Long) {
        Log.d(LOG_TAG, "Number of elements: ${counts.size}")
        Log.d(LOG_TAG, "Execution Time: $time ms")
    }
}