package com.fernandocejas.sample.threading.data

import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class Pages(private val start: Int, private val end: Int, private val file: File) : Iterable<Page> {
    private val LOG_TAG = Pages::class.java.canonicalName

    var elementsProcessed: Int = 0
    var fileParsingTime: Long = 0
    var elementsProcessedTime: Long = 0

    companion object {
        fun empty() = Pages(0, 0, File(""))
    }

    override fun iterator() = PageIterator()

    inner class PageIterator : Iterator<Page> {
        private val xmlDoc: Document
        private val pagesList: NodeList

        private var cursor = start

        init {
            elementsProcessed = 0
            elementsProcessedTime = 0

            val fileParsingStartTime = System.currentTimeMillis()
            xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
            pagesList = xmlDoc.getElementsByTagName("page")
            fileParsingTime = System.currentTimeMillis().minus(fileParsingStartTime)
        }

        override fun hasNext(): Boolean {
            val hasNext = cursor < end
            if (!hasNext) {
                Log.d(LOG_TAG, "Time Parsing XML File: $fileParsingTime ms")
                Log.d(LOG_TAG, "Time Processing XML Node Elements: $elementsProcessedTime ms")
                Log.d(LOG_TAG, "Total Time: ${fileParsingTime.plus(elementsProcessedTime)} ms")
            }
            return hasNext
        }

        override fun next(): Page {
            if (hasNext()) {
                val startParsingNodeTime = System.currentTimeMillis()
                var page = Page("", "")
                try {
                    val node = pagesList.item(cursor)
                    if (node != null) {
                        val title = node.childNodes.item(1).textContent
                        val text = node.childNodes.item(7).childNodes.item(15).textContent
                        page = Page(title, text)
                        elementsProcessed++
                    }
                } catch (exception: Exception) {
                    //TODO: handle exceptions properly
                } finally {
                    cursor++
                    elementsProcessedTime = elementsProcessedTime.plus(System.currentTimeMillis().minus(startParsingNodeTime))
                    return page
                }
            }
            throw NoSuchElementException()
        }
    }
}