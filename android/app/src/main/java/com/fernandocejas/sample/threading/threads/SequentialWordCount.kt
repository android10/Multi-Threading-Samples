package com.fernandocejas.sample.threading.threads

import android.util.Log
import com.fernandocejas.sample.threading.data.Pages
import com.fernandocejas.sample.threading.data.Source
import com.fernandocejas.sample.threading.data.Words
import kotlin.system.measureTimeMillis

class SequentialWordCount {
    private val LOG_TAG = SequentialWordCount::class.java.canonicalName

    private val counts: HashMap<String, Int?> = HashMap()

    fun run() {
        val time = measureTimeMillis {
            Thread {
                val pagesOne = Pages(0, 700, Source().wikiPagesBatchOne())
                pagesOne.forEach { page -> Words(page.text).forEach { countWord(it) } }

                val pagesTwo = Pages(0, 700, Source().wikiPagesBatchTwo())
                pagesTwo.forEach { page -> Words(page.text).forEach { countWord(it) } }
            }.start()
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