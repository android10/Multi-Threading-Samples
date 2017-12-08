package com.fernandocejas.sample.threading.threads

import android.util.Log
import com.fernandocejas.sample.threading.data.Pages
import com.fernandocejas.sample.threading.data.Source
import com.fernandocejas.sample.threading.data.Words

class WordCount {
    private val counts: HashMap<String, Int> = HashMap()

    fun runSequentially() {
        val startTime = System.currentTimeMillis()
        var endTime = System.currentTimeMillis()

        val myThread = Thread {
            val pages01 = Pages(0, 5000, Source().wikiPages01())
            for (page in pages01) {
                val words = Words(page.text)
                for (word in words) {
                    countWord(word)
                }
            }

            val pages02 = Pages(0, 5000, Source().wikiPages02())
            for (page in pages02) {
                val words = Words(page.text)
                for (word in words) {
                    countWord(word)
                }
            }

            endTime = System.currentTimeMillis()
        }

        myThread.run()

        Log.d("WordCount", "Number of elements: " + counts.size)
        Log.d("WordCount", "Time: " + (endTime - startTime) + "ms")
    }

    private fun countWord(word: String) {
        val currentCount = counts[word]

        if (currentCount == null) {
            counts[word] = 1
        } else {
            counts[word] = currentCount + 1
        }
    }
}