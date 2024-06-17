package kr.blugon.sethome.commands

import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import kr.blugon.kotlinbrigadier.BrigadierCommand
import kr.blugon.kotlinbrigadier.player
import kr.blugon.minicolor.MiniColor
import kr.blugon.minicolor.MiniColor.Companion.miniMessage
import kr.blugon.sethome.Sethome.Companion.homeYaml
import kr.blugon.sethome.Sethome.Companion.homes
import kr.blugon.sethome.Sethome.Companion.saveHomes
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.entity.Player
import java.util.*


fun BrigadierCommand.deleteHomeCommand() {
    register("delhome", "Delete home") {
        require { sender is Player }
        then("home" to string()) {
            builder.suggests { context, suggestionsBuilder ->
                val nowArg = context.input.split(" ").getOrElse(1) {""}
                suggestionsBuilder.apply {
                    for (home in context.player.homes) {
                        if(home.key.lowercase().startsWith(nowArg.lowercase())) {
                            this.suggest(home.key)
                        }
                    }
                }.buildFuture()
            }
            executes {
                val homeName = getString(this, "home")
                if(player.homes[homeName] == null) {
                    player.sendMessage("${MiniColor.YELLOW}$homeName${MiniColor.RED}가 존재하지 않습니다".miniMessage)
                    return@executes false
                }
                player.homes.remove(homeName)
                player.sendMessage("${MiniColor.YELLOW}$homeName${MiniColor.WHITE}을 삭제했습니다".miniMessage)
                homeYaml.set("${player.uniqueId}.${homeName}", null)
                player.saveHomes()
                true
            }
        }
    }
}