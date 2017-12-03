package com.fernandocejas.sample.threading.data

import java.text.BreakIterator

class Words(private val text: String) : Iterable<String> {

    override fun iterator() = WordIterator()

    inner class WordIterator : Iterator<String> {
        private val wordBoundary = BreakIterator.getWordInstance()

        private var start: Int
        private var end: Int

        init {
            wordBoundary.setText(text)
            start = wordBoundary.first()
            end = wordBoundary.next()
        }

        override fun hasNext() = throw UnsupportedOperationException()

        override fun next(): String {
            val string = text.substring(start, end)
            start = end
            end = wordBoundary.next()
            return string
        }
    }
}