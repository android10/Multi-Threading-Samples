package com.fernandocejas.sample.threading.threads

import android.util.Log
import com.fernandocejas.sample.threading.data.Pages

class WordCount {
    fun runSequentially() {
        val pages = Pages(0, 150)
        for (page in pages) {
            Log.d("WordCount", page.title)
        }
    }
}