package com.fernandocejas.sample.threading.data

import android.os.Environment
import java.io.File

class Source {
    //TODO: Properly copy the file when the app is being installed for the first time
    fun data() = File(Environment.getExternalStorageDirectory().path + File.separator +
            "/fernandocejas/wiki_pages_small.xml")
}