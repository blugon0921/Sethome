package kr.blugon.sethome.commands

import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import kr.blugon.kotlinbrigadier.BrigadierCommand
import kr.blugon.kotlinbrigadier.player
import kr.blugon.minicolor.MiniColor
import kr.blugon.minicolor.MiniColor.Companion.miniMessage
import kr.blugon.sethome.Sethome.Companion.homes
import kr.blugon.sethome.Sethome.Companion.saveHomes
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

fun BrigadierCommand.setHomeCommand() {
    register("sethome", "Set home") {
        require { sender is Player }
        then("home" to string()) {
            "confirm" {
                executes {
                    val homeName = getString(this, "home")
                    player.homes[homeName] = player.location
                    if(player.homes[homeName] != null) player.sendMessage("현재 위치를 ${MiniColor.YELLOW}${homeName}${MiniColor.YELLOW.close}에 덮어 씌웠습니다".miniMessage)
                    else player.sendMessage("현재 위치를 ${MiniColor.YELLOW}${homeName}${MiniColor.YELLOW.close}에 저장했습니다".miniMessage)
                    player.saveHomes()
                    return@executes true
                }
            }

            executes {
                val homeName = getString(this, "home")
                if(player.homes[homeName] != null) {
                    player.sendMessage(("${MiniColor.YELLOW}${homeName}${MiniColor.YELLOW.close}은(는) 이미 존재하는 집입니다 \n " +
                                "덮어 씌우려면 '/sethome $homeName confirm'을 입력해 주세요\n" +
                                "${MiniColor.RED}※주의※ 한번 덮어 씌운 집은 되돌릴 수 없습니다").miniMessage)
                    return@executes false
                }
                player.homes[homeName] = player.location
                player.sendMessage("현재 위치를 ${MiniColor.YELLOW}${homeName}${MiniColor.YELLOW.close}에 저장했습니다".miniMessage)
                player.saveHomes()
                true
            }
        }
    }
}