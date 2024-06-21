package kr.blugon.sethome.commands

import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.string
import kr.blugon.kotlinbrigadier.BrigadierCommand
import kr.blugon.kotlinbrigadier.getValue
import kr.blugon.kotlinbrigadier.player
import kr.blugon.minicolor.MiniColor
import kr.blugon.minicolor.MiniColor.Companion.miniMessage
import kr.blugon.sethome.Sethome.Companion.homes
import kr.blugon.sethome.Sethome.Companion.saveHomes
import org.bukkit.entity.Player

fun BrigadierCommand.setHomeCommand() {
    register("sethome", "Set home") {
        require { sender is Player }
        then("home" to string()) {
            "confirm" {
                executes {
                    val home: String by it
                    if(home == "RespawnPoint") {
                        player.sendRichMessage("${MiniColor.RED}해당 이름은 사용할 수 없습니다")
                        return@executes false
                    }
                    player.homes[home] = player.location
                    if(player.homes[home] != null) player.sendRichMessage("현재 위치를 ${MiniColor.YELLOW}${home}${MiniColor.YELLOW.close}에 덮어 씌웠습니다")
                    else player.sendRichMessage("현재 위치를 ${MiniColor.YELLOW}${home}${MiniColor.YELLOW.close}에 저장했습니다")
                    player.saveHomes()
                    return@executes true
                }
            }

            executes {
                val home: String by it
                if(home == "RespawnPoint") {
                    player.sendRichMessage("${MiniColor.RED}해당 이름은 사용할 수 없습니다")
                    return@executes false
                }
                if(player.homes[home] != null) {
                    player.sendRichMessage("${MiniColor.YELLOW}${home}${MiniColor.YELLOW.close}은(는) 이미 존재하는 집입니다 \n " +
                                "덮어 씌우려면 '/sethome $home confirm'을 입력해 주세요\n" +
                                "${MiniColor.RED}※주의※ 한번 덮어 씌운 집은 되돌릴 수 없습니다")
                    return@executes false
                }
                player.homes[home] = player.location
                player.sendRichMessage("현재 위치를 ${MiniColor.YELLOW}${home}${MiniColor.YELLOW.close}에 저장했습니다")
                player.saveHomes()
                true
            }
        }
    }
}