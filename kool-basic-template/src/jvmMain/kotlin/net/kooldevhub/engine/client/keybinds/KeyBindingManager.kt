package net.kooldevhub.engine.client.keybinds

import de.fabmax.kool.input.InputStack
import de.fabmax.kool.input.KeyEvent
import de.fabmax.kool.input.UniversalKeyCode
import net.kooldevhub.engine.client.settings.GameSettings

data class KeyBinding(
        val keyCode: UniversalKeyCode,
        val name: String,
        val filter: (KeyEvent) -> Boolean = { it.isPressed },
        val callback: (KeyEvent) -> Unit
)

class KeyBindingManager(private val settings: GameSettings) {
    private val inputHandler: InputStack.InputHandler = InputStack.defaultInputHandler

    private val keyBindings = mutableListOf<KeyBinding>()

    init {
        registerKeyBindings()
    }

    private fun registerKeyBindings() {
        for (binding in GameKeyBindings.values()) {
            val key = settings.keyBindings[binding.cmd]
            inputHandler.addKeyListener(
                    keyCode = key!!,
                    name = binding.cmd
            ) { event ->
                binding.callback(event)
            }
        }
    }

    fun registerKeyBinding(keyBinding: KeyBinding) {
        inputHandler.addKeyListener(
                keyCode = keyBinding.keyCode,
                name = keyBinding.name,
                filter = keyBinding.filter,
                callback = keyBinding.callback
        )
        keyBindings.add(keyBinding)
    }
}