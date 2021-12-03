package io.unthrottled.intellijpowerplugin

import com.intellij.openapi.Disposable
import com.intellij.openapi.rd.paint2DLine
import com.intellij.ui.components.JBLayeredPane
import com.intellij.ui.jcef.HwFacadeJPanel
import com.intellij.ui.paint.LinePainter2D
import com.intellij.util.Alarm
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Point
import javax.swing.JLayeredPane

class PowerCanvas(
  private val rootPane: JLayeredPane,
  private val startingPoint: Point,
) : HwFacadeJPanel(), Disposable {

  companion object {
    val PANEL_LAYER: Int = JBLayeredPane.DRAG_LAYER
  }

  private val fadeoutAlarm = Alarm()

  private val stoppingPoint: Point = getStoppingPoint()

  private fun getStoppingPoint(): Point {
    val rootSize = rootPane.size
    val rootWidth = rootSize.width
    val midPoint = rootWidth / 2
    val moveLeft = midPoint - startingPoint.x < 0
    return if(moveLeft) {
      Point(0, rootSize.height)
    } else {
      Point(rootWidth, rootSize.height)
    }
  }

  init {
    isOpaque = false
    layout = null
    this.size = rootPane.size
    setLocation(0, 0)
  }

  fun display() {
    rootPane.add(this)
    rootPane.setLayer(this, PANEL_LAYER, -1)

    fadeoutAlarm.addRequest({
      remove()
    }, 2000)
  }

  override fun paintComponent(g: Graphics) {
    super.paintComponent(g)
    if (g !is Graphics2D) return

    g.paint2DLine(
      startingPoint,
      stoppingPoint,
      LinePainter2D.StrokeType.INSIDE,
      2.0,
      Color.CYAN
    )
  }

  private fun remove() {
    rootPane.remove(this)
    rootPane.revalidate()
    rootPane.repaint()
  }

  override fun dispose() {
    fadeoutAlarm.dispose()
  }
}