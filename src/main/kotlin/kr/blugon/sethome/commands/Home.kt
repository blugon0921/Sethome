package kr.blugon.sethome.commands

import com.mojang.brigadier.arguments.StringArgumentType.string
import kr.blugon.kotlinbrigadier.BrigadierCommand
import kr.blugon.kotlinbrigadier.getValue
import kr.blugon.kotlinbrigadier.player
import kr.blugon.minicolor.MiniColor
import kr.blugon.sethome.Sethome.Companion.homes
import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun BrigadierCommand.homeCommand() {
    register("home", "Teleport to home") {
        require { sender is Player }
        then("home" to string()) {
            suggests {
                mutableListOf<String>().apply {
                    this.addAll(player.homes.keys)
                    this.add("RespawnPoint")
                }
            }
            executes {
                val home: String by it
                if(home == "RespawnPoint") {
                    if(player.respawnLocation != null) player.teleport(player.respawnLocation!!)
                    else player.teleport(Bukkit.getWorld("world")!!.spawnLocation)
                    player.sendRichMessage("${MiniColor.YELLOW}리스폰 포인트${MiniColor.WHITE}로 순간이동 했습니다")
                }
                if(player.homes[home] == null) {
                    player.sendRichMessage("${MiniColor.YELLOW}$home${MiniColor.RED}(이)가 존재하지 않습니다")
                }
                player.teleport(player.homes[home]!!)
                player.sendRichMessage("${MiniColor.YELLOW}$home${MiniColor.WHITE}(으)로 순간이동 했습니다")
            }
        }
    }
}