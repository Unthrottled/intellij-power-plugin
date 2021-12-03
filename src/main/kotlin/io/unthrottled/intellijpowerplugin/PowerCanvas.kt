package io.unthrottled.intellijpowerplugin

import com.intellij.openapi.Disposable
import com.intellij.ui.components.JBLayeredPane
import com.intellij.ui.jcef.HwFacadeJPanel
import com.intellij.util.Alarm
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JLayeredPane

class PowerCanvas(
  private val rootPane: JLayeredPane,
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

    g.color = Color.PINK
    g.fillRect(40, 40, 40, 40)
  }

  private fun remove() {
    rootPane.remove(this)
  }

  override fun dispose() {
    fadeoutAlarm.dispose()
  }
}