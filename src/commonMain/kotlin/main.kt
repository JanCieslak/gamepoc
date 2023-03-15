import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.*
import com.soywiz.korge.box2d.*
import com.soywiz.korge.input.*
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.tween.*
import com.soywiz.korgw.*
import com.soywiz.korim.color.*
import com.soywiz.korma.geom.*
import com.soywiz.korma.random.*
import org.jbox2d.callbacks.*
import org.jbox2d.collision.shapes.*
import org.jbox2d.common.*
import org.jbox2d.dynamics.*
import org.jbox2d.dynamics.joints.*
import javax.swing.GrayFilter
import kotlin.random.*

suspend fun main() = Korge(
    width = 1280,
    height = 768,
    bgcolor = Colors["#2b2b2b"],
    quality = GameWindow.Quality.PERFORMANCE,
) {
    solidRect(5000.0, height * 3, Colors.TRANSPARENT_BLACK)
        .xy(-5000.0, -height)
        .registerBodyWithFixture(type = BodyType.STATIC)
    solidRect(5000.0, height * 3, Colors.TRANSPARENT_BLACK)
        .xy(width, -height)
        .registerBodyWithFixture(type = BodyType.STATIC)
    solidRect(width * 3, 5000.0, Colors.TRANSPARENT_BLACK)
        .xy(-width, -5000.0)
        .registerBodyWithFixture(type = BodyType.STATIC)
    solidRect(width * 3, 5000.0, Colors.TRANSPARENT_BLACK)
        .xy(-width, height)
        .registerBodyWithFixture(type = BodyType.STATIC)

    val c = circle(16.0)
        .position(200, 200)
        .registerBodyWithFixture(
            type = BodyType.DYNAMIC,
            shape = CircleShape(0.95),
            gravityScale = 0
        )

    val player = circle(16.0)
        .position(50, 0)
        .registerBodyWithFixture(
            type = BodyType.DYNAMIC,
            shape = CircleShape(0.95),
            gravityScale = 0
        )
        .addUpdater {
            val dt = it / 16.milliseconds

            if (input.keys[Key.W]) y -= 2 * dt
            if (input.keys[Key.S]) y += 2 * dt
            if (input.keys[Key.A]) x -= 2 * dt
            if (input.keys[Key.D]) x += 2 * dt
            if (input.keys[Key.SPACE]) {
                if (collidesWith(c)) {
                    val direction = Vector2D.direction(pos, c.pos).mul(20)
                    c.body?.applyLinearImpulse(
                        Vec2(direction.x.toFloat(), direction.y.toFloat()),
                        c.body?.worldCenter!!,
//                        Vec2(c.pos.x.toFloat() + c.radius.toFloat() / 2.0f, c.pos.y.toFloat() + c.radius.toFloat() / 2.0f),
                        false
                    )
                }
            }
        }
}

//class SimpleBox2dScene : ShowScene() {
//    val random = Random(0L)
//
//    override suspend fun SContainer.sceneMain() {
//        val stage = stage!!
//        fixedSizeContainer(stage.width, stage.height) {
//            solidRect(50, 50, Colors.RED).position(400, 50).rotation(30.degrees)
//                .registerBodyWithFixture(type = BodyType.DYNAMIC, density = 2, friction = 0.01)
//            solidRect(50, 50, Colors.RED).position(300, 100).registerBodyWithFixture(type = BodyType.DYNAMIC)
//            solidRect(50, 50, Colors.RED).position(450, 100).rotation(15.degrees)
//                .registerBodyWithFixture(type = BodyType.DYNAMIC)
//            solidRect(600, 100, Colors.WHITE).position(100, 500).registerBodyWithFixture(
//                type = BodyType.STATIC,
//                friction = 0.2
//            )
//
//            onClick {
//                val pos = it.currentPosLocal
//                solidRect(50, 50, Colors.RED)
//                    .position(pos.x, pos.y)
//                    .rotation(random[0.degrees, 90.degrees])
//                    .also { it.colorMul = random[Colors.RED, Colors.PURPLE] }
//                    .registerBodyWithFixture(type = BodyType.DYNAMIC)
//            }
//
//            uiButton(text = "Reset").position(stage.width - 128.0, 0.0).onClick { sceneContainer.changeTo(this@SimpleBox2dScene::class) }
//        }
//    }
//
//    // @TODO: Will be available on the next version of korma
//    private fun Random.nextDoubleInclusive() = (this.nextInt(0x1000001).toDouble() / 0x1000000.toDouble())
//    private operator fun Random.get(l: Angle, r: Angle): Angle = this.nextDoubleInclusive().interpolate(l, r)
//}
