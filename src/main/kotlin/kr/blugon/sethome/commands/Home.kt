package kr.blugon.sethome.commands

import com.mojang.brigadier.arguments.IntegerArgumentType.integer
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import kr.blugon.kotlinbrigadier.BrigadierCommand
import kr.blugon.kotlinbrigadier.player
import kr.blugon.minicolor.MiniColor
import kr.blugon.minicolor.MiniColor.Companion.miniMessage
import kr.blugon.sethome.Sethome.Companion.homes
import kr.blugon.sethome.Sethome.Companion.saveHomes
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.entity.Player
import java.util.*

fun BrigadierCommand.homeCommand() {
    register("home", "Teleport to home") {
        require { sender is Player }
        then("home" to StringArgumentType.word()) {
            builder.suggests { context, suggestionsBuilder ->
                val nowArg = context.input.split(" ").getOrElse(1) {""}
                suggestionsBuilder.apply {
                    for (home in context.player.homes) {
                        if(home.key.lowercase().startsWith(nowArg.lowercase())) {
                            this.suggest(home.key)
                        }
                    }
                    if("RespawnPoint".lowercase().startsWith(nowArg.lowercase())) {
                        this.suggest("RespawnPoint")
                    }
                }.buildFuture()
            }
            executes {
                val homeName = getString(this, "home")
                if(homeName == "RespawnPoint") {
                    if(player.respawnLocation != null) player.teleport(player.respawnLocation!!)
                    else player.teleport(Bukkit.getWorld("world")!!.spawnLocation)
                    player.sendMessage("${MiniColor.YELLOW}리스폰 포인트${MiniColor.WHITE}로 순간이동 했습니다".miniMessage)
                    return@executes false
                }
                if(player.homes[homeName] == null) {
                    player.sendMessage("${MiniColor.YELLOW}$homeName${MiniColor.RED}가 존재하지 않습니다".miniMessage)
                    return@executes false
                }
                player.teleport(player.homes[homeName]!!)
                player.sendMessage("${MiniColor.YELLOW}$homeName${MiniColor.WHITE}로 순간이동 했습니다".miniMessage)
                true
            }
        }
    }
}