package io.unthrottled.intellijpowerplugin

class PowerCore: PowerEventListener {
  override fun onDispatch(powerEvent: PowerEvent) {
    println("let's go!")
  }
}