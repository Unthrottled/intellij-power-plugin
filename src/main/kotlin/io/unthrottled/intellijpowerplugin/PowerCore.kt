package io.unthrottled.intellijpowerplugin

import com.intellij.ide.navigationToolbar.NavBarRootPaneExtension
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.impl.ActionButton
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.IdeFrame
import com.intellij.openapi.wm.WindowManager
import com.intellij.openapi.wm.ex.IdeFrameEx
import com.intellij.util.ui.UIUtil
import java.awt.Point
import javax.swing.SwingUtilities

class PowerCore : PowerEventListener {
  override fun onDispatch(powerEvent: PowerEvent) {
    val project = powerEvent.project

    val ideFrame = getIDEFrame(project)

    getRootPane(project).toOptional()
      .ifPresent { rootPane ->
        PowerCanvas(
          rootPane,
          getStartingPoint(ideFrame)
        ).display()
      }
  }

  private fun getStartingPoint(ideFrame: IdeFrame): Point {
    // todo: make more safe
    return if (ideFrame is IdeFrameEx) {
      val navBarExt = ideFrame.getNorthExtension(NavBarRootPaneExtension.NAV_BAR)
      val component = navBarExt?.component
      val compileButton = component.toOptional()
        .flatMap { naveBarComponent ->
          val actionManager = ApplicationManager.getApplication().getServiceIfCreated(ActionManager::class.java)
          UIToolBox.findDescendant(naveBarComponent) { child ->
            child is ActionButton &&
                "CompileDirty".equals(actionManager?.getId(child.action), ignoreCase = true)
          }
        }.orElse(null)
      val point = compileButton?.locationOnScreen ?: Point(0, 0)
      val size = compileButton.size
      point.x += size?.width?.div(2) ?: 0
      point.y += size?.height?.div(2) ?: 0
      SwingUtilities.convertPointFromScreen(point, ideFrame.component)
      point
    } else {
      Point(0, 0)
    }
  }

  private fun getRootPane(project: Project) = UIUtil.getRootPane(
    getIDEFrame(project).component
  )?.layeredPane

  private fun getIDEFrame(project: Project): IdeFrame =
    WindowManager.getInstance().getIdeFrame(project)
      ?: WindowManager.getInstance().allProjectFrames.first()


}


