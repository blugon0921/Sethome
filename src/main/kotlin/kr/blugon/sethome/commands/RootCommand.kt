package kr.blugon.sethome.commands

import kr.blugon.kotlinbrigadier.registerEventHandler
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerCommands() {
    val manager = this.lifecycleManager
    manager.registerEventHandler {
        setHomeCommand()
        homeCommand()
        deleteHomeCommand()
    }
}