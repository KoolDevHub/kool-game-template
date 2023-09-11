package net.kooldevhub.engine.client.keybinds

import de.fabmax.kool.input.KeyEvent
import de.fabmax.kool.input.UniversalKeyCode

enum class GameKeyBindings(val cmd: String, val defaultKeyCode: UniversalKeyCode) {
    UPWARD("UPWARD", UniversalKeyCode(-27, "CURSOR_UP")) {
        override var callback: (KeyEvent) -> Unit = { println("$cmd default trigger") }
    },
    DOWNWARD("DOWNWARD", UniversalKeyCode(-28, "CURSOR_DOWN")) {
        override var callback: (KeyEvent) -> Unit = { println("$cmd default triggered") }
    };

    abstract var callback: (KeyEvent) -> Unit
}