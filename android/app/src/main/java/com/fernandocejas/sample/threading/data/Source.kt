package com.fernandocejas.sample.threading.data

import android.os.Environment
import java.io.File

class Source {
    private val FILES_DIRECTORY = "/fernandocejas/"
    private val FILE_NAME_WIKI_PAGES_01 = "wiki_pages_big_01.xml"
    private val FILE_NAME_WIKI_PAGES_02 = "wiki_pages_big_02.xml"

    fun wikiPages01() = wikiPages(FILE_NAME_WIKI_PAGES_01)

    fun wikiPages02() = wikiPages(FILE_NAME_WIKI_PAGES_02)

    private fun wikiPages(fileName: String) =
            File(Environment.getExternalStorageDirectory().path + FILES_DIRECTORY + fileName)
}