package kr.blugon.sethome.commands

import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import kr.blugon.kotlinbrigadier.BrigadierCommand
import kr.blugon.kotlinbrigadier.getValue
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
            suggests {
                mutableListOf<String>().apply {
                    player.homes.keys.forEach {
                        this.add("\"${it}\"")
                    }
                }
            }
            executes {
                val home: String by it
                if(player.homes[home] == null) {
                    player.sendRichMessage("${MiniColor.YELLOW}$home${MiniColor.RED}(이)가 존재하지 않습니다")
                }
                player.homes.remove(home)
                player.sendRichMessage("${MiniColor.YELLOW}$home${MiniColor.WHITE}을(를) 삭제했습니다")
                homeYaml.set("${player.uniqueId}.${home}", null)
                player.saveHomes()
                true
            }
        }
    }
}