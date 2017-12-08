package com.fernandocejas.sample.threading.threads

import android.util.Log
import com.fernandocejas.sample.threading.data.Pages
import com.fernandocejas.sample.threading.data.Source
import com.fernandocejas.sample.threading.data.Words
import kotlin.system.measureTimeMillis

class WordCount {
    private val LOG_TAG = WordCount::class.java.canonicalName

    private val counts: HashMap<String, Int?> = HashMap()

    fun runSequentially() {
        val time = measureTimeMillis {
            Thread {
                val pagesOne = Pages(0, 5000, Source().wikiPagesBatchOne())
                pagesOne.forEach { page -> Words(page.text).forEach { countWord(it) } }

                val pagesTwo = Pages(0, 5000, Source().wikiPagesBatchTwo())
                pagesTwo.forEach { page -> Words(page.text).forEach { countWord(it) } }
            }.run()
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