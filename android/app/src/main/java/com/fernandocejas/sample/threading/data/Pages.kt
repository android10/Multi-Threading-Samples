package com.fernandocejas.sample.threading.data

import android.util.Log
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class Pages(private val start: Int, private val end: Int, private val file: File) : Iterable<Page> {
    private val LOG_TAG = Pages::class.java.canonicalName

    companion object {
        fun empty() = Pages(0, 0, File(""))
    }

    override fun iterator() = PageIterator()

    inner class PageIterator : Iterator<Page> {
        private val xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
        private val pagesList = xmlDoc.getElementsByTagName("page")

        private var cursor = start

        //For performance measurement
        private var startTime: Long = 0
        init {
            startTime = System.currentTimeMillis()
        }

        override fun hasNext(): Boolean {
            val hasNext = cursor < end
            if (!hasNext) {
                val totalExecutionTime = System.currentTimeMillis().minus(startTime)
                Log.d(LOG_TAG, "Parsing XML Execution Time: $totalExecutionTime ms")
            }
            return hasNext
        }

        override fun next(): Page {
            if (hasNext()) {
                var page = Page("", "")
                try {
                    val node = pagesList.item(cursor)
                    if (node != null) {
                        val title = node.childNodes.item(1).textContent
                        val text = node.childNodes.item(7).childNodes.item(15).textContent
                        page = Page(title, text)
                    }
                } catch (exception: Exception) {
                    //TODO: handle exceptions properly
                } finally {
                    cursor++
                    return page
                }
            }
            throw NoSuchElementException()
        }
    }
}