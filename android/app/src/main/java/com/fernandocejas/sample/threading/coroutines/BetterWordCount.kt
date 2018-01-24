package com.fernandocejas.sample.threading.coroutines

import android.util.Log
import com.fernandocejas.sample.threading.data.Pages
import com.fernandocejas.sample.threading.data.Source
import com.fernandocejas.sample.threading.data.Words
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.io.File
import kotlin.system.measureTimeMillis

class BetterWordCount(source: Source) {
    private val LOG_TAG = BetterWordCount::class.java.canonicalName

    private val filePagesOne = source.wikiPagesBatchOne()
    private val filePagesTwo = source.wikiPagesBatchTwo()

    fun run() {
        launch(CommonPool) {
            val time = measureTimeMillis {
                val one = async(CommonPool) { counter(0.rangeTo(349), filePagesOne) }
                val two = async(CommonPool) { counter(350.rangeTo(700), filePagesOne) }
                val three = async(CommonPool) { counter(0.rangeTo(349), filePagesTwo) }
                val four = async(CommonPool) { counter(350.rangeTo(700), filePagesTwo) }
                one.await(); two.await(); three.await(); four.await()
            }
            logData(time)
        }
    }

    private suspend fun counter(range: IntRange, file: File): HashMap<String, Int?> {
        val counts: HashMap<String, Int?> = HashMap()
        val pages = Pages(range.start, range.endInclusive, file)
        pages.forEach { page -> Words(page.text).forEach { countWord(counts, it) } }
        return counts
    }

    private fun countWord(counts: HashMap<String, Int?>, word: String) {
        when(counts.containsKey(word)) {
            true -> counts[word] = counts[word]?.plus(1)
            false -> counts[word] = 1
        }
    }

    private fun logData(time: Long) {
        Log.d(LOG_TAG, "Execution Time: $time ms")
    }
}