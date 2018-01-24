package com.fernandocejas.sample.threading.coroutines

import android.util.Log
import com.fernandocejas.sample.threading.data.Pages
import com.fernandocejas.sample.threading.data.Source
import com.fernandocejas.sample.threading.data.Words
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

class TwoThreadsWordCount {
    private val counts: ConcurrentHashMap<String, Int?> = ConcurrentHashMap()

    private val LOG_TAG = TwoThreadsWordCount::class.java.canonicalName

    fun run() {
        val poolContext = newFixedThreadPoolContext(2, "ThreadPool")
        launch(poolContext) {
            val time = measureTimeMillis {
                val one = async(poolContext) { counterPages1() }
                val two = async(poolContext) { counterPages2() }
                one.await()
                two.await()
            }
            logData(time)
        }
    }

    private suspend fun counterPages1() {
        val pagesOne = Pages(0, 700, Source().wikiPagesBatchOne())
        pagesOne.forEach { page -> Words(page.text).forEach { countWord(it) } }
    }

    private suspend fun counterPages2() {
        val pagesTwo = Pages(0, 700, Source().wikiPagesBatchTwo())
        pagesTwo.forEach { page -> Words(page.text).forEach { countWord(it) } }
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