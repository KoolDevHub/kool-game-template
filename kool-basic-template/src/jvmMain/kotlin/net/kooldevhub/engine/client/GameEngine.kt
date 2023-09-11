package net.kooldevhub.engine.client

import de.fabmax.kool.KoolApplication
import de.fabmax.kool.KoolConfig
import de.fabmax.kool.math.Vec2i
import de.fabmax.kool.platform.Lwjgl3Context
import net.kooldevhub.engine.client.keybinds.KeyBindingManager
import net.kooldevhub.engine.client.settings.GameSettings
import net.kooldevhub.engine.launchApp
import java.io.File

class GameEngine {

    var engine: GameEngine = this

    val gameDir = File(File(System.getProperty("user.dir")).parent, "run")

    lateinit var settings: GameSettings
        private set
    val keyBindingManager: KeyBindingManager

    var ticks: Long = 0L
        private set
    var accumulator = 0.0
        private set
    var lastTime = 0.0
        private set

    init {
        loadSettings()
        keyBindingManager = KeyBindingManager(settings)

        startContext()
    }

    fun startContext() {
        KoolApplication(
                config = KoolConfig(
                        windowTitle = "Kool Game Template",
                        renderBackend = Lwjgl3Context.Backend.OPEN_GL,
                        windowSize = Vec2i(settings.displayWidth, settings.displayHeight),
                        isFullscreen = settings.fullscreen,
                        isVsync = true
                )
        ) { ctx ->
            lastTime = System.nanoTime() / 1_000_000_000.0
            ctx.onRender.add {
                val currentTime = System.nanoTime() / 1_000_000_000.0
                val frameTime = currentTime - lastTime
                lastTime = currentTime
                accumulator += frameTime

                val tickRate = 1.0 / 30.0  // 30Hz

                while (accumulator >= tickRate) {
                    update()
                    accumulator -= tickRate
                }
            }
            launchApp(ctx)
        }
    }

    fun loadSettings() {
        GameSettings.initialize(gameDir)
        settings = GameSettings.load()!!
    }

    fun update() {
        ticks++
        // Your game logic here
    }
}
