package com.fernandocejas.sample.threading.data

import java.text.BreakIterator

class Words(private val text: String = "This is defensive test") : Iterable<String> {

    override fun iterator() = WordIterator()

    inner class WordIterator : Iterator<String> {
        private val wordBoundary = BreakIterator.getWordInstance()

        private var start: Int
        private var next: Int

        init {
            wordBoundary.setText(text)
            start = wordBoundary.first()
            next = wordBoundary.next()
        }

        override fun hasNext() = next != BreakIterator.DONE

        override fun next(): String {
            if (hasNext()) {
                val string = text.substring(start, next)
                start = next
                next = wordBoundary.next()
                return string
            }
            throw NoSuchElementException()
        }
    }
}