package kr.blugon.sethome.commands

import kr.blugon.sethome.Sethome.Companion.homes
import kr.blugon.sethome.Sethome.Companion.saveHomes
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.entity.Player
import java.util.*

class SetHome(private val player: Player, private val command: Command, private val label: String, private val args: Array<out String>): ChildCommand {

    override fun onCommand() {
        if(command.name != "sethome") return
        if(args.size == 2) {
            if(args[1] != "confirm") return player.sendMessage(text("${args[1]}은 잘못된 인수입니다").color(NamedTextColor.RED))
            player.homes[args[0]] = player.location
            if(player.homes[args[0]] != null) player.sendMessage(text("현재 위치를 ").append(text(args[0]).color(NamedTextColor.YELLOW)).append(text("에 덮어 씌웠습니다").color(NamedTextColor.WHITE)))
            else player.sendMessage(text("현재 위치를 ").append(text(args[0]).color(NamedTextColor.YELLOW)).append(text("에 저장했습니다").color(NamedTextColor.WHITE)))
            player.saveHomes()
            return
        }
        if(player.homes[args[0]] != null) {
            return player.sendMessage(
                text(args[0]).color(NamedTextColor.YELLOW)
                    .append(text("은 이미 존재 하는 집입니다").color(NamedTextColor.WHITE))
                    .append(text("\n덮어 씌우려면 '/sethome ${args[0]} confirm'을 입력해 주세요").color(NamedTextColor.WHITE))
                    .append(text("\n※주의※ 한번 덮어 씌운 집은 되돌릴 수 없습니다").color(NamedTextColor.RED))
            )
        }
        player.homes[args[0]] = player.location
        player.sendMessage(text("현재 위치를 ").append(text(args[0]).color(NamedTextColor.YELLOW)).append(text("에 저장했습니다").color(NamedTextColor.WHITE)))
        player.saveHomes()
    }

    override val tabCompleteValues: MutableList<String>
        get() {
            if(command.name != "sethome") return Collections.emptyList()
            return if(args.size == 2) arrayListOf("confirm")
            else Collections.emptyList()
        }
}