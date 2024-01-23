package kr.blugon.sethome.commands

import kr.blugon.sethome.Sethome.Companion.homes
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.entity.Player
import java.util.*

class Home(private val player: Player, private val command: Command, private val label: String, private val args: Array<out String>):
    ChildCommand {

    override fun onCommand() {
        if(command.name != "home") return
        if(args.size != 1) {
            return player.sendMessage(text("인수의 크기는 1개여야 합니다").color(NamedTextColor.RED))
        }
        if(args[0] == "RespawnPoint") {
            if(player.bedSpawnLocation != null) player.teleport(player.bedSpawnLocation!!)
            else player.teleport(Bukkit.getWorld("world")!!.spawnLocation)
            return player.sendMessage(text("리스폰 포인트").color(NamedTextColor.YELLOW).append(text("로 순간이동 했습니다").color(NamedTextColor.WHITE)))
        }
        if(player.homes[args[0]] == null) {
            return player.sendMessage(text(args[0]).color(NamedTextColor.YELLOW).append(text("가 존재하지 않습니다").color(NamedTextColor.RED)))
        }
        player.teleport(player.homes[args[0]]!!)
        player.sendMessage(text(args[0]).color(NamedTextColor.YELLOW).append(text("로 순간이동 했습니다").color(NamedTextColor.WHITE)))
    }

    override val tabCompleteValues: MutableList<String>
        get() {
            if(command.name != "home") return Collections.emptyList()
            val names = arrayListOf("RespawnPoint")
            for(home in player.homes) {
                names.add(home.key)
            }
            return names
        }
}