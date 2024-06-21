package kr.blugon.sethome.commands

import kr.blugon.kotlinbrigadier.registerCommandHandler
import org.bukkit.plugin.java.JavaPlugin

fun JavaPlugin.registerCommands() {
    val manager = this.lifecycleManager
    manager.registerCommandHandler {
        setHomeCommand()
        homeCommand()
        deleteHomeCommand()
    }
}