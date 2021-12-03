package io.unthrottled.intellijpowerplugin

import com.intellij.util.ui.UIUtil
import java.awt.Component
import java.util.*
import java.util.function.Predicate

object UIToolBox {

  fun findDescendant(root: Component, predicate: Predicate<Component>): Optional<Component> {
    val queue = LinkedList<Component>()
    queue.offer(root)
    while (queue.isNotEmpty()) {
      val current = queue.poll()
      if (predicate.test(current)) {
        return current.toOptional()
      }
      UIUtil.uiChildren(current)
        .filterNotNull()
        .forEach { queue.offer(it) }
    }
    return Optional.empty()
  }
}