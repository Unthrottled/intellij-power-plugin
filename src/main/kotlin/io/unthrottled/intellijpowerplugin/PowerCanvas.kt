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
import javax.swing.JLayeredPane

class PowerCanvas(
  private val rootPane: JLayeredPane,
  private val lightningSegments: List<LightningSegment>,
) : HwFacadeJPanel(), Disposable {

  companion object {
    val PANEL_LAYER: Int = JBLayeredPane.DRAG_LAYER
  }

  private val fadeoutAlarm = Alarm()

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

    lightningSegments.forEach {
      g.paint2DLine(
        it.start,
        it.stop,
        LinePainter2D.StrokeType.INSIDE,
        2.0,
        Color.CYAN,
      )
    }
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


