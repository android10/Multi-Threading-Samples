package com.fernandocejas.sample.threading.rxjava

import android.util.Log
import com.fernandocejas.sample.threading.data.Pages
import com.fernandocejas.sample.threading.data.Source
import com.fernandocejas.sample.threading.data.Words
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class WordCount {
    private val LOG_TAG = WordCount::class.java.canonicalName

    private val counts: HashMap<String, Int?> = HashMap()

    fun runSequentially() {
        val startTime = System.currentTimeMillis()

        val observable = Observable.fromCallable {
            val pagesOne = Pages(0, 5000, Source().wikiPagesBatchOne())
            pagesOne.forEach { page -> Words(page.text).forEach { countWord(it) } }

            val pagesTwo = Pages(0, 5000, Source().wikiPagesBatchTwo())
            pagesTwo.forEach { page -> Words(page.text).forEach { countWord(it) } }
        }

        observable
                .doOnComplete { logData(System.currentTimeMillis() - startTime) }
                .subscribeOn(Schedulers.single())
                .subscribe()
    }

    private fun countWord(word: String) {
        when(counts.containsKey(word)) {
            true -> counts[word] = counts[word]?.plus(1)
            false -> counts[word] = 1
        }
    }

    private fun logData(time: Long) {
        Log.d(LOG_TAG, "Number of elements: ${counts.size}")
        Log.d(LOG_TAG, "Execution Time: $time ms")
    }
}