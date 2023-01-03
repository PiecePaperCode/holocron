package model

import com.google.gson.Gson
import java.io.File
import java.io.FileWriter

class SafeFile {
    private val gson = Gson()
    fun save(tournament: Tournament) {
        val json = gson.toJson(tournament)
        FileWriter("holocron_save.json", false).use {
            it.write(json)
        }
    }
    fun load(): Tournament {
        val file = File("holocron_save.json")
        return if (file.exists()) {
            gson.fromJson(file.readText(), Tournament::class.java)
        } else {
            Tournament()
        }
    }
    fun delete() {
        File("holocron_save.json").delete()
    }
}