package io.unthrottled.intellijpowerplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware

class ActivatePowerBuild : AnAction(), DumbAware {
  override fun actionPerformed(e: AnActionEvent) {
    val project = e.project ?: return
    project.messageBus.syncPublisher(EVENT_TOPIC).onDispatch(
      PowerEvent(PowerEvents.TASK, project)
    )
  }
}


