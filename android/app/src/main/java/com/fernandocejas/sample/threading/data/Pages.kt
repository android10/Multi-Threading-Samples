package com.fernandocejas.sample.threading.data

import javax.xml.parsers.DocumentBuilderFactory

class Pages(private val start: Int, private val end: Int) : Iterable<Page> {

    override fun iterator() = PageIterator()

    inner class PageIterator : Iterator<Page> {
        private val xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Source().data())
        private val pagesList = xmlDoc.getElementsByTagName("page")

        private var cursor = start

        override fun hasNext() = cursor < end

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
                    //TODO: this code must be refactored in order to properly handle exceptions
                } finally {
                    cursor++
                    return page
                }
            }
            throw NoSuchElementException()
        }
    }
}