package io.unthrottled.intellijpowerplugin

import java.awt.Point
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

data class LightningSegment(
  val start: Point,
  val stop: Point,
)

class ThorCore {


  private val ranbo = Random(System.currentTimeMillis())

  fun summonLightning(
    start: Point,
    stop: Point
  ): List<LightningSegment> {
    val deltaX = stop.x - start.x
    val stepsX = ranbo.nextInt(400)
    val stepX = deltaX / stepsX
    val deltaY = stop.y - start.y
    val stepsY = ranbo.nextInt(400)
    val stepY = deltaY / stepsY
    val segments = max(stepsX, stepsY)
    val lightningStuff = ArrayList<LightningSegment>(segments)
    var currentStart = start
    repeat(segments) {
      val x = if (it <= stepsX) {
        currentStart.x + stepX
      } else {
        stop.x
      }
      val y = if (it <= stepsY) {
        currentStart.y + stepY
      } else {
        stop.y
      }
      val nextPoint = Point(x, y)
      lightningStuff.add(
        LightningSegment(start = currentStart, stop = nextPoint)
      )

      currentStart = nextPoint
    }


    return lightningStuff
  }

}