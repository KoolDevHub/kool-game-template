package net.kooldevhub.engine

import net.kooldevhub.engine.client.GameEngine
/**
 * JVM main function / app entry point: Creates a new KoolContext (with optional platform-specific configuration) and
 * forwards it to the common-code launcher.
 */
fun main() {
    GameEngine()
}