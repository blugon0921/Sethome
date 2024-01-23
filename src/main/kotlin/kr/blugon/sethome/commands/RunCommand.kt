package kr.blugon.sethome.commands

import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.*

class RunCommand: CommandExecutor, TabCompleter {

    override fun onCommand(player: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(player !is Player) {
            player.sendMessage(text("해당 명령어는 플레이어만 사용할 수 있습니다").color(NamedTextColor.RED))
            return false
        }
        SetHome(player, command, label, args).onCommand()
        Home(player, command, label, args).onCommand()
        DelHome(player, command, label, args).onCommand()
        return true
    }

    override fun onTabComplete(player: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String> {
        val returns = ArrayList<String>()
        if(player !is Player) return returns
        returns.addAll(SetHome(player, command, label, args).tabCompleteValues)
        returns.addAll(Home(player, command, label, args).tabCompleteValues)
        returns.addAll(DelHome(player, command, label, args).tabCompleteValues)
        val final = ArrayList<String>()
        for(r in returns) {
            if(r.lowercase().startsWith(args[args.size-1].lowercase())) final.add(r)
        }
        return final
    }
}

interface ChildCommand {
    fun onCommand() {}
    val tabCompleteValues: MutableList<String>
}