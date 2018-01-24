package com.fernandocejas.sample.threading.threads

import android.util.Log
import com.fernandocejas.sample.threading.data.Pages
import com.fernandocejas.sample.threading.data.Source
import com.fernandocejas.sample.threading.data.Words
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

class TwoThreadsWordCount {
    private val LOG_TAG = TwoThreadsWordCount::class.java.canonicalName

    private val counts: ConcurrentHashMap<String, Int?> = ConcurrentHashMap()

    fun run() {
        val threadOne = Thread {
            val pagesOne = Pages(0, 700, Source().wikiPagesBatchOne())
            pagesOne.forEach { page -> Words(page.text).forEach { countWord(it) } }
        }

        val threadTwo = Thread {
            val pagesTwo = Pages(0, 700, Source().wikiPagesBatchTwo())
            pagesTwo.forEach { page -> Words(page.text).forEach { countWord(it) } }
        }

        val time = measureTimeMillis {
            threadOne.start()
            threadTwo.start()
            threadOne.join()
            threadTwo.join()
        }

        Log.d(LOG_TAG, "Number of elements: ${counts.size}")
        Log.d(LOG_TAG, "Execution Time: $time ms")
    }

    private fun countWord(word: String) {
        when(counts.containsKey(word)) {
            true -> counts[word] = counts[word]?.plus(1)
            false -> counts[word] = 1
        }
    }
}