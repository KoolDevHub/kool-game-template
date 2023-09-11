package net.kooldevhub.engine.client.settings

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import de.fabmax.kool.input.UniversalKeyCode
import net.kooldevhub.engine.client.keybinds.GameKeyBindings
import java.io.File
import java.io.FileReader
import java.io.FileWriter

data class GameSettings(
        var displayWidth: Int = 800,
        var displayHeight: Int = 600,
        var fullscreen: Boolean = false,
        var audioVolume: Float = 0.5f,
        var keyBindings: MutableMap<String, UniversalKeyCode> = GameKeyBindings.values().associate { it.cmd to it.defaultKeyCode }.toMutableMap()
) {
    companion object {
        private var gameDir: File? = null
        private const val settingsFileName = "game_settings.json"
        private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

        fun initialize(gameDir: File) {
            this.gameDir = gameDir
            if (!gameDir.exists()) {
                gameDir.mkdirs()
            }
        }

        fun load(): GameSettings {
            val file = File(gameDir, settingsFileName).apply {
                if (!exists()) GameSettings().save()
            }
            return try {
                FileReader(file).use { gson.fromJson(it, GameSettings::class.java) }
            } catch (e: Exception) {
                println("Could not read settings; default settings will be used.")
                GameSettings()
            }
        }
    }

    fun save() {
        val file = File(gameDir, settingsFileName)
        FileWriter(file).use { gson.toJson(this, it) }
    }

    fun modifyAndSave(block: GameSettings.() -> Unit) {
        this.apply(block).save()
    }
}