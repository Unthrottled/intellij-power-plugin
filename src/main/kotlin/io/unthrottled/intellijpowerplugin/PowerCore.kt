package io.unthrottled.intellijpowerplugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import com.intellij.util.ui.UIUtil

class PowerCore : PowerEventListener {
  override fun onDispatch(powerEvent: PowerEvent) {
    getRootPane(powerEvent.project).toOptional()
      .ifPresent { rootPane ->
        PowerCanvas(rootPane).display()
      }
  }

  private fun getRootPane(project: Project) = UIUtil.getRootPane(
    getIDEFrame(project).component
  )?.layeredPane

  private fun getIDEFrame(project: Project) =
    WindowManager.getInstance().getIdeFrame(project)
      ?: WindowManager.getInstance().allProjectFrames.first()


}