package com.fernandocejas.sample.threading.coroutines

import android.annotation.TargetApi
import android.os.Build.VERSION_CODES
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
                mergeResults(one.await(), two.await(), three.await(), four.await())
            }
            logData(time)
        }
    }

    private fun counter(range: IntRange, file: File): HashMap<String, Int?> {
        val counts: HashMap<String, Int?> = HashMap()
        val pages = Pages(range.start, range.endInclusive, file)
        pages.forEach { page -> Words(page.text).forEach { countWord(counts, it) } }
        return counts
    }

    private fun countWord(counts: HashMap<String, Int?>, word: String) =
            counts.merge(word, 1) { oldValue, _ -> oldValue.plus(1) }

    private fun logData(time: Long) {
        Log.d(LOG_TAG, "Execution Time: $time ms")
    }

    private fun mergeResults(vararg results: HashMap<String, Int?>) {
        //TODO: implement this and refactor num
        //TODO: use the function mergeReduce below to merge results.
    }

    //TODO: improve this function
    @TargetApi(VERSION_CODES.N)
    fun <K, V> Map<K, V>.mergeReduce(other: Map<K, V>, reduce: (V, V) -> V = { _, b -> b }): Map<K, V> =
            this.toMutableMap().apply { other.forEach { merge(it.key, it.value, reduce) } }
}