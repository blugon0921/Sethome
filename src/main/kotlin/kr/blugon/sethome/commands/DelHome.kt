package kr.blugon.sethome.commands

import kr.blugon.sethome.Sethome.Companion.homeYaml
import kr.blugon.sethome.Sethome.Companion.homes
import kr.blugon.sethome.Sethome.Companion.saveHomes
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.entity.Player
import java.util.*

class DelHome(private val player: Player, private val command: Command, private val label: String, private val args: Array<out String>): ChildCommand {

    override fun onCommand() {
        if(command.name != "delhome") return
        if(args.size != 1) {
            return player.sendMessage(text("인수의 크기는 1개여야 합니다").color(NamedTextColor.RED))
        }
        if(player.homes[args[0]] == null) {
            return player.sendMessage(text(args[0]).color(NamedTextColor.YELLOW).append(text("가 존재하지 않습니다").color(NamedTextColor.RED)))
        }
        player.homes.remove(args[0])
        player.sendMessage(text(args[0]).color(NamedTextColor.YELLOW).append(text("을 삭제했습니다").color(NamedTextColor.WHITE)))
        homeYaml.set("${player.uniqueId}.${args[0]}", null)
        player.saveHomes()
    }

    override val tabCompleteValues: MutableList<String>
        get() {
            if(command.name != "delhome") return Collections.emptyList()
            val names = ArrayList<String>()
            for(home in player.homes) {
                names.add(home.key)
            }
            return names
        }
}