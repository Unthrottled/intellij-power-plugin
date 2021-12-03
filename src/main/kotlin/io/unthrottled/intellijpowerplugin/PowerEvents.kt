package io.unthrottled.intellijpowerplugin

import com.intellij.openapi.project.Project
import com.intellij.util.messages.Topic

@Suppress("MagicNumber")
enum class PowerEvents(val value: Int) {
  // these are part of the same group
  IDLE(1 shl 0),
  RETURN(1 shl 0),

  LOGS(1 shl 1),
  ON_DEMAND(1 shl 2),
  PROCESS(1 shl 3),
  STARTUP(1 shl 4),
  TASK(1 shl 5),
  TEST(1 shl 6),
  RELAX(1 shl 7),
  SILENCE(1 shl 8),
}

data class PowerEvent(
  val type: PowerEvents,
  val project: Project,
)

interface PowerEventListener {
  fun onDispatch(powerEvent: PowerEvent)
}

val EVENT_TOPIC: Topic<PowerEventListener> =
  Topic(PowerEventListener::class.java)

