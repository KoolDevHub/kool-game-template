package net.kooldevhub.engine

import de.fabmax.kool.KoolContext
import de.fabmax.kool.math.Vec3f
import de.fabmax.kool.modules.ksl.KslPbrShader
import de.fabmax.kool.scene.colorMesh
import de.fabmax.kool.scene.defaultOrbitCamera
import de.fabmax.kool.scene.scene
import de.fabmax.kool.util.Color
import de.fabmax.kool.util.Time
import de.fabmax.kool.util.debugOverlay

/**
 * Main application entry. This demo creates a small example scene, which you probably want to replace by your actual
 * game / application content.
 */
fun launchApp(ctx: KoolContext) {


    // add a hello-world demo scene
    ctx.scenes += scene {
        // enable simple camera mouse control
        defaultOrbitCamera()

        // add a spinning color cube to the scene
        colorMesh {
            generate {
                // called once on init: generates a cube with different (vertex-)colors assigned to each side
                cube(centered = true) {
                    colored()
                }
            }
            onUpdate {
                // called on each frame: spins the cube around its x-axis
                transform.rotate(Time.deltaT * 45f, Vec3f.X_AXIS)
            }
            // assign a shader, which uses the vertex color info
            shader = KslPbrShader {
                color { vertexColor() }
                metallic(0f)
                roughness(0.25f)
            }
        }

        // set up a single light source
        lighting.singleLight {
            setDirectional(Vec3f(-1f, -1f, -1f))
            setColor(Color.WHITE, 5f)
        }
    }

    // add the debugOverlay. provides an fps counter and some additional debug info
    ctx.scenes += debugOverlay()
}